import React, { useState } from 'react';
import axios from 'axios';
import {
    Zap, Play, Loader2, CheckCircle2, BarChart3, Clock, Users,
    TrendingUp, PieChart as PieChartIcon, Eye, ListFilter, X
} from 'lucide-react';
import {
    BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
    LineChart, Line, Cell
} from 'recharts';

const API_BASE = 'http://localhost:8080/api';

const COLORS = ['#8b4513', '#d2691e', '#f4a460']; // Coffee-inspired colors

const Simulation = () => {
    const [testCases, setTestCases] = useState(5);
    const [loading, setLoading] = useState(false);
    const [results, setResults] = useState([]);
    const [selectedDetail, setSelectedDetail] = useState(null);

    const runSimulation = async () => {
        setLoading(true);
        setSelectedDetail(null);
        try {
            const res = await axios.get(`${API_BASE}/simulation/batch/run?testCases=${testCases}`);
            setResults(res.data);
        } catch (error) {
            console.error("Simulation failed:", error);
            alert("Failed to run simulation. Ensure backend is running.");
        } finally {
            setLoading(false);
        }
    };

    // Prepare data for charts
    const baristaAggregatedData = [
        { name: 'Barista 1', total: results.reduce((sum, res) => sum + (res.baristaPerformance['Barista 1'] || 0), 0) },
        { name: 'Barista 2', total: results.reduce((sum, res) => sum + (res.baristaPerformance['Barista 2'] || 0), 0) },
        { name: 'Barista 3', total: results.reduce((sum, res) => sum + (res.baristaPerformance['Barista 3'] || 0), 0) }
    ];

    const waitTimeTrendData = results.map(res => ({
        name: `#${res.testCaseId}`,
        waitTime: Math.round(res.averageWaitTime)
    }));

    return (
        <div className="container" style={{ paddingBottom: '4rem', position: 'relative' }}>
            <header style={{ marginBottom: '2rem' }}>
                <h1 className="title">Batch Simulation</h1>
                <p className="subtitle">Run high-volume performance tests (200-300 orders per case)</p>
            </header>

            {results.length > 0 && (
                <div className="card" style={{ marginBottom: '2rem', padding: '1.5rem', background: 'var(--card-bg)' }}>
                    <h2 className="title" style={{ fontSize: '1.1rem', marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                        <TrendingUp size={18} color="var(--primary)" /> Batch Simulation Summary
                    </h2>
                    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1.5rem' }}>
                        <div style={{ padding: '1rem', borderRight: '1px solid #eee' }}>
                            <div className="subtitle" style={{ fontSize: '0.7rem' }}>OVERALL AVG WAIT</div>
                            <div style={{ fontSize: '1.5rem', fontWeight: 800, color: 'var(--primary)' }}>
                                {(() => {
                                    const avg = results.reduce((acc, curr) => acc + curr.averageWaitTime, 0) / results.length;
                                    return `${Math.floor(avg / 60)}m ${Math.round(avg % 60)}s`;
                                })()}
                            </div>
                        </div>
                        <div style={{ padding: '1rem', borderRight: '1px solid #eee' }}>
                            <div className="subtitle" style={{ fontSize: '0.7rem' }}>AGGREGATE VOLUME</div>
                            <div style={{ fontSize: '1.5rem', fontWeight: 800 }}>
                                {results.reduce((acc, curr) => acc + curr.totalOrders, 0)} <small style={{ fontSize: '0.8rem', opacity: 0.5 }}>orders</small>
                            </div>
                        </div>
                        <div style={{ padding: '1rem' }}>
                            <div className="subtitle" style={{ fontSize: '0.7rem', color: 'var(--danger)' }}>TOTAL ABANDONED</div>
                            <div style={{ fontSize: '1.5rem', fontWeight: 800, color: 'var(--danger)' }}>
                                {results.reduce((acc, curr) => acc + curr.abandonedOrders, 0)}
                            </div>
                        </div>
                    </div>
                </div>
            )}

            <div className="card" style={{ marginBottom: '2rem' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '2rem' }}>
                    <div style={{ flex: 1 }}>
                        <label style={{ display: 'block', fontWeight: 700, marginBottom: '0.5rem', opacity: 0.7 }}>
                            Number of Test Cases
                        </label>
                        <input
                            type="number"
                            value={testCases}
                            onChange={(e) => setTestCases(parseInt(e.target.value))}
                            min="1"
                            max="20"
                            style={{
                                width: '100%',
                                padding: '0.8rem',
                                borderRadius: '12px',
                                border: '2px solid #eee',
                                fontSize: '1rem',
                                fontWeight: 600
                            }}
                        />
                    </div>
                    <button
                        className="primary"
                        onClick={runSimulation}
                        disabled={loading}
                        style={{ height: 'fit-content', padding: '1rem 2rem' }}
                    >
                        {loading ? <Loader2 className="spin" /> : <Play size={18} />}
                        {loading ? 'Simulating...' : 'Execute Batch'}
                    </button>
                </div>
            </div>

            {results.length > 0 && (
                <div style={{ display: 'flex', flexDirection: 'column', gap: '2rem' }}>
                    {/* Charts Section */}
                    <div style={{ display: 'grid', gridTemplateColumns: '1.2fr 1fr', gap: '1.5rem' }}>
                        <div className="card">
                            <h3 style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1.5rem', fontSize: '1.1rem' }}>
                                <TrendingUp size={20} style={{ color: 'var(--primary)' }} /> Wait Time Trend (seconds)
                            </h3>
                            <div style={{ width: '100%', height: 300 }}>
                                <ResponsiveContainer>
                                    <LineChart data={waitTimeTrendData} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
                                        <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f0f0f0" />
                                        <XAxis dataKey="name" axisLine={false} tickLine={false} tick={{ fill: '#999', fontSize: 12 }} dy={10} />
                                        <YAxis axisLine={false} tickLine={false} tick={{ fill: '#999', fontSize: 12 }} />
                                        <Tooltip
                                            contentStyle={{ borderRadius: '12px', border: 'none', boxShadow: '0 4px 20px rgba(0,0,0,0.1)', padding: '12px' }}
                                            formatter={(value) => [`${value}s`, 'Avg Wait']}
                                        />
                                        <Line
                                            type="monotone"
                                            dataKey="waitTime"
                                            stroke="var(--primary)"
                                            strokeWidth={4}
                                            dot={{ r: 6, fill: 'var(--primary)', strokeWidth: 2, stroke: '#fff' }}
                                            activeDot={{ r: 8, strokeWidth: 0 }}
                                            animationDuration={1500}
                                        />
                                    </LineChart>
                                </ResponsiveContainer>
                            </div>
                        </div>

                        <div className="card">
                            <h3 style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1.5rem', fontSize: '1.1rem' }}>
                                <PieChartIcon size={20} style={{ color: '#d2691e' }} /> Barista Workload Distribution
                            </h3>
                            <div style={{ width: '100%', height: 300 }}>
                                <ResponsiveContainer>
                                    <BarChart data={baristaAggregatedData} layout="vertical" margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                                        <CartesianGrid strokeDasharray="3 3" horizontal={false} stroke="#f0f0f0" />
                                        <XAxis type="number" hide />
                                        <YAxis dataKey="name" type="category" axisLine={false} tickLine={false} tick={{ fill: '#444', fontWeight: 600 }} />
                                        <Tooltip
                                            cursor={{ fill: 'rgba(0,0,0,0.02)' }}
                                            contentStyle={{ borderRadius: '12px', border: 'none', boxShadow: '0 4px 20px rgba(0,0,0,0.1)' }}
                                            formatter={(value) => [value, 'Total Drinks']}
                                        />
                                        <Bar dataKey="total" radius={[0, 10, 10, 0]} barSize={35} animationDuration={1000}>
                                            {baristaAggregatedData.map((entry, index) => (
                                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                            ))}
                                        </Bar>
                                    </BarChart>
                                </ResponsiveContainer>
                            </div>
                        </div>
                    </div>

                    <div className="grid-dashboard" style={{ gridTemplateColumns: '1fr' }}>
                        <section className="card">
                            <h2 style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1.5rem' }}>
                                <BarChart3 size={20} /> Results Overview
                            </h2>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Test Case</th>
                                        <th>Volume</th>
                                        <th>Abandoned</th>
                                        <th>Avg Wait Time</th>
                                        <th style={{ textAlign: 'center' }}>Barista 1</th>
                                        <th style={{ textAlign: 'center' }}>Barista 2</th>
                                        <th style={{ textAlign: 'center' }}>Barista 3</th>
                                        <th style={{ textAlign: 'right' }}>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {results.map((res, index) => (
                                        <tr key={index}>
                                            <td style={{ fontWeight: 600, color: 'var(--text-muted)' }}>#{res.testCaseId}</td>
                                            <td style={{ fontWeight: 700 }}>{res.totalOrders} orders</td>
                                            <td style={{ fontWeight: 700, color: res.abandonedOrders > 0 ? 'var(--danger)' : 'inherit' }}>
                                                {res.abandonedOrders}
                                            </td>
                                            <td style={{ fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
                                                <Clock size={14} style={{ opacity: 0.5 }} />
                                                <span style={{ fontWeight: 800, color: 'var(--primary)' }}>
                                                    {Math.floor(res.averageWaitTime / 60)}m {Math.round(res.averageWaitTime % 60)}s
                                                </span>
                                            </td>
                                            <td>
                                                <div className="badge badge-new" style={{ minWidth: '40px', textAlign: 'center' }}>
                                                    {res.baristaPerformance['Barista 1']}
                                                </div>
                                            </td>
                                            <td>
                                                <div className="badge badge-new" style={{ minWidth: '40px', textAlign: 'center' }}>
                                                    {res.baristaPerformance['Barista 2']}
                                                </div>
                                            </td>
                                            <td>
                                                <div className="badge badge-new" style={{ minWidth: '40px', textAlign: 'center' }}>
                                                    {res.baristaPerformance['Barista 3']}
                                                </div>
                                            </td>
                                            <td style={{ textAlign: 'right' }}>
                                                <button
                                                    className="badge badge-gold nav-item-hover"
                                                    style={{ border: 'none', cursor: 'pointer', display: 'inline-flex', alignItems: 'center', gap: '0.4rem' }}
                                                    onClick={() => setSelectedDetail(res)}
                                                >
                                                    <Eye size={12} /> View Details
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </section>
                    </div>
                </div>
            )}

            {/* Details Panel - Slider */}
            {selectedDetail && (
                <div style={{
                    position: 'fixed',
                    top: 0,
                    right: 0,
                    width: '600px',
                    height: '100vh',
                    background: 'white',
                    boxShadow: '-10px 0 30px rgba(0,0,0,0.1)',
                    zIndex: 1000,
                    padding: '2rem',
                    overflowY: 'auto'
                }}>
                    <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                        <div>
                            <h2 className="title" style={{ fontSize: '1.5rem', marginBottom: '0.2rem' }}>Test Case #{selectedDetail.testCaseId}</h2>
                            <p className="subtitle">Detailed Order Log ({selectedDetail.totalOrders} orders)</p>
                        </div>
                        <button
                            onClick={() => setSelectedDetail(null)}
                            style={{ background: 'none', border: 'none', cursor: 'pointer', opacity: 0.4 }}
                        >
                            <X size={24} />
                        </button>
                    </header>

                    <div className="grid-dashboard" style={{ gridTemplateColumns: '1fr', gap: '1rem' }}>
                        <div className="card" style={{ padding: '0', overflow: 'hidden' }}>
                            <table style={{ margin: 0 }}>
                                <thead style={{ background: '#f8f9fa' }}>
                                    <tr>
                                        <th>Time</th>
                                        <th>User</th>
                                        <th>Order</th>
                                        <th>Prep</th>
                                        <th>Wait</th>
                                        <th>Assigned To</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {selectedDetail.orders.map((order, idx) => (
                                        <tr key={idx} style={{ opacity: order.abandoned ? 0.6 : 1 }}>
                                            <td style={{
                                                fontWeight: 600,
                                                textDecoration: order.abandoned ? 'line-through' : 'none'
                                            }}>
                                                T+{Math.floor(order.arrivalTime)}m {Math.round((order.arrivalTime % 1) * 60)}s
                                            </td>
                                            <td>
                                                <div className={`badge ${order.customerType === 'NEW' ? 'badge-new' : 'badge-gold'}`} style={{ fontSize: '0.65rem' }}>
                                                    {order.customerType}
                                                </div>
                                            </td>
                                            <td style={{ fontWeight: 700, fontSize: '0.8rem', textDecoration: order.abandoned ? 'line-through' : 'none', opacity: 0.8 }}>
                                                {order.drinkType}
                                            </td>
                                            <td style={{ textDecoration: order.abandoned ? 'line-through' : 'none' }}>
                                                {order.abandoned ? '-' : `${order.prepTime}s`}
                                            </td>
                                            <td>
                                                <span style={{
                                                    fontWeight: 800,
                                                    color: order.abandoned ? 'var(--danger)' : (order.waitTime > 300 ? 'var(--warning)' : 'inherit'),
                                                    textDecoration: order.abandoned ? 'line-through' : 'none'
                                                }}>
                                                    {Math.floor(order.waitTime / 60)}m {Math.round(order.waitTime % 60)}s
                                                </span>
                                                {order.abandoned && <span style={{ marginLeft: '8px', fontSize: '0.6rem', color: 'var(--danger)', fontWeight: 900 }}>[ABANDONED]</span>}
                                            </td>
                                            <td>
                                                <div className={`badge ${order.abandoned ? 'badge-muted' : 'badge-new'}`} style={{ fontSize: '0.7rem' }}>
                                                    {order.abandoned ? 'N/A' : order.baristaName}
                                                </div>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Simulation;
