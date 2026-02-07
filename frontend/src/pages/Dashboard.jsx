import React from 'react';
import { Users, Timer, UserCheck, TrendingUp, Clock, Zap } from 'lucide-react';

const Dashboard = ({ queue, baristas }) => {
    const avgWait = queue.length > 0
        ? Math.round(queue.reduce((acc, curr) => acc + curr.waitTime, 0) / queue.length / 60)
        : 0;
    const busyBaristas = baristas.filter(b => !b.available).length;
    // Active Pipeline = Waiting + Preparing + Ready
    const activePipeline = queue.filter(o => ['WAITING', 'PROCESSING', 'READY'].includes(o.status));

    return (
        <div className="container">
            <header style={{ marginBottom: '2rem' }}>
                <h1 className="title">Executive Summary</h1>
                <p className="subtitle">Real-time performance metrics</p>
            </header>

            <div className="stat-bar">
                <div className="stat-item">
                    <span className="stat-label"><Timer size={14} style={{ marginRight: 4 }} /> Avg. Wait Time</span>
                    <span className="stat-value">{avgWait}m <small style={{ fontSize: '0.8rem', opacity: 0.5 }}>Target: &lt;8m</small></span>
                </div>
                <div className="stat-item">
                    <span className="stat-label"><UserCheck size={14} style={{ marginRight: 4 }} /> Staff Active</span>
                    <span className="stat-value">{busyBaristas} / {baristas.length} <small style={{ fontSize: '0.8rem', opacity: 0.5 }}>Baristas</small></span>
                </div>
                <div className="stat-item">
                    <span className="stat-label"><TrendingUp size={14} style={{ marginRight: 4 }} /> Total Load</span>
                    <span className="stat-value">
                        {Math.round(baristas.reduce((acc, b) => acc + b.currentLoad, 0) / baristas.length * 100)}%
                        <small style={{ fontSize: '0.8rem', opacity: 0.5 }}>Efficiency</small>
                    </span>
                </div>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 400px', gap: '2rem' }}>
                <section className="card">
                    <h2 style={{ fontSize: '1.25rem', marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                        <Users size={18} /> Barista Squad Status
                    </h2>
                    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', gap: '1.5rem' }}>
                        {baristas.map(barista => (
                            <div key={barista.id} className="barista-card" style={{ position: 'relative' }}>
                                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                    <div style={{ fontWeight: 800 }}>{barista.name}</div>
                                    <div style={{ fontSize: '1.1rem', fontWeight: 800, color: 'var(--primary)' }}>
                                        {Math.round(barista.currentLoad * 100)}%
                                    </div>
                                </div>
                                <div style={{ fontSize: '0.7rem', color: barista.available ? 'var(--success)' : 'var(--warning)', fontWeight: 700, margin: '0.5rem 0' }}>
                                    {barista.available ? '● ONLINE' : '● BUSY'}
                                </div>
                                <div className="progress-bar" style={{ height: '6px' }}>
                                    <div className="progress-fill" style={{
                                        width: `${Math.min(100, barista.currentLoad * 100)}%`,
                                        backgroundColor: barista.currentLoad > 0.9 ? 'var(--danger)' : barista.currentLoad > 0.7 ? 'var(--gold)' : 'var(--success)'
                                    }}></div>
                                </div>
                                {!barista.available && barista.currentDrink && (
                                    <div style={{ fontSize: '0.65rem', marginTop: '0.5rem', opacity: 0.6, fontWeight: 700 }}>
                                        Making: {barista.currentDrink}
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                </section>

                <section className="card" style={{ padding: 0 }}>
                    <h2 style={{ fontSize: '1.1rem', padding: '1.5rem', borderBottom: '1px solid #eee', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                        <Zap size={16} color="var(--primary)" /> Active Pipeline ({activePipeline.length})
                    </h2>
                    <div style={{ maxHeight: '450px', overflowY: 'auto' }}>
                        {activePipeline.length === 0 ? (
                            <p style={{ padding: '2rem', textAlign: 'center', opacity: 0.5 }}>No active orders</p>
                        ) : (
                            <table style={{ margin: 0 }}>
                                <tbody>
                                    {activePipeline.map(order => (
                                        <tr key={order.id}>
                                            <td style={{ width: '40px', opacity: 0.3, fontWeight: 800 }}>#{order.id}</td>
                                            <td>
                                                <div style={{ fontWeight: 700 }}>{order.drinkType}</div>
                                                <div style={{ fontSize: '0.7rem', opacity: 0.6 }}>{order.customerName}</div>
                                            </td>
                                            <td style={{ textAlign: 'right' }}>
                                                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-end', gap: '0.25rem' }}>
                                                    <span className={`badge badge-${order.status === 'WAITING' ? 'gold' :
                                                        order.status === 'PROCESSING' ? 'primary' : 'success'
                                                        }`} style={{ fontSize: '0.6rem', padding: '0.2rem 0.4rem' }}>
                                                        {order.status}
                                                    </span>
                                                    <span className="priority-score" style={{ fontSize: '0.8rem', opacity: 1 }}>
                                                        {order.priorityScore ? order.priorityScore.toFixed(1) : '0.0'}
                                                    </span>
                                                </div>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        )}
                    </div>
                </section>
            </div>
        </div>
    );
};

export default Dashboard;
