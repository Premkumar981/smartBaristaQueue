import React from 'react';
import { Zap, Coffee, TrendingUp, AlertCircle, CheckCircle, Clock } from 'lucide-react';

const Orders = ({ queue, fetchData }) => {
    return (
        <div className="container">
            <header style={{ marginBottom: '2rem' }}>
                <h1>Live Queue</h1>
                <p>Real-time order tracking & history</p>
            </header>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 300px', gap: '2rem' }}>
                <section className="card">
                    <h2 style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1.5rem' }}>
                        <Zap size={18} /> Queue Pipeline
                    </h2>
                    <table style={{ margin: 0 }}>
                        <thead>
                            <tr>
                                <th>#ID</th>
                                <th>Customer</th>
                                <th>Drink</th>
                                <th>Status</th>
                                <th style={{ textAlign: 'center' }}>Priority</th>
                                <th style={{ textAlign: 'right' }}>Wait Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            {queue.length === 0 ? (
                                <tr>
                                    <td colSpan="5" style={{ textAlign: 'center', padding: '4rem', opacity: 0.5 }}>
                                        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '1rem' }}>
                                            <Coffee size={48} />
                                            Queue is empty
                                        </div>
                                    </td>
                                </tr>
                            ) : (
                                queue.map(order => (
                                    <tr key={order.id} className={order.status === 'READY' ? 'pulse' : ''}>
                                        <td style={{ fontWeight: 800, opacity: 0.4 }}>#{order.id}</td>
                                        <td>
                                            <div style={{ fontWeight: 700 }}>{order.customerName}</div>
                                            <div style={{ fontSize: '0.7rem', opacity: 0.6 }}>{order.customerType} • {order.loyaltyStatus}</div>
                                        </td>
                                        <td>
                                            <div style={{ fontWeight: 700 }}>{order.drinkType}</div>
                                            <div style={{ fontSize: '0.7rem', opacity: 0.6 }}>{order.prepTime}m prep</div>
                                        </td>
                                        <td>
                                            <span className={`badge badge-${order.status === 'WAITING' ? 'gold' :
                                                order.status === 'PROCESSING' ? 'primary' :
                                                    order.status === 'READY' ? 'success' :
                                                        order.status === 'COMPLETED' ? 'success' : 'danger'
                                                }`} style={{ fontSize: '0.7rem', opacity: order.status === 'COMPLETED' ? 0.6 : 1 }}>
                                                {order.status}
                                            </span>
                                        </td>
                                        <td style={{ textAlign: 'center' }}>
                                            <span className="priority-score" style={{ fontSize: '1rem' }}>
                                                {order.priorityScore ? order.priorityScore.toFixed(1) : '0.0'}
                                            </span>
                                            {order.priorityReason && (
                                                <div style={{ fontSize: '0.6rem', opacity: 0.5, maxWidth: '100px', margin: '0 auto', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }} title={order.priorityReason}>
                                                    {order.priorityReason}
                                                </div>
                                            )}
                                        </td>
                                        <td style={{ textAlign: 'right' }}>
                                            {order.status === 'COMPLETED' || order.status === 'ABANDONED' ? (
                                                <span style={{ opacity: 0.4 }}>--</span>
                                            ) : (
                                                <div className="wait-timer">
                                                    {Math.floor(order.waitTime / 60)}:{String(order.waitTime % 60).padStart(2, '0')}
                                                </div>
                                            )}
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </table>
                </section>

                <aside>
                    <section className="card" style={{ marginBottom: '1.5rem', background: 'var(--card-bg)' }}>
                        <h3 style={{ fontSize: '0.9rem', display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1rem' }}>
                            <TrendingUp size={14} /> Pricing
                        </h3>
                        <div style={{ fontSize: '0.9rem' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                <span>Cold Brew</span>
                                <b>₹120</b>
                            </div>
                            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                <span>Espresso</span>
                                <b>₹150</b>
                            </div>
                            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                <span>Americano</span>
                                <b>₹140</b>
                            </div>
                            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                <span>Cappuccino</span>
                                <b>₹180</b>
                            </div>
                            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                <span>Latte</span>
                                <b>₹200</b>
                            </div>
                            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                                <span>Mocha</span>
                                <b>₹250</b>
                            </div>
                        </div>
                    </section>

                    <section className="card" style={{ background: '#5d4037', color: 'white' }}>
                        <h3 style={{ fontSize: '0.9rem', display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1rem' }}>
                            <AlertCircle size={14} /> Rules
                        </h3>
                        <ul style={{ fontSize: '0.8rem', paddingLeft: '1.2rem', margin: 0 }}>
                            <li style={{ marginBottom: '0.5rem' }}>8m for New Customers</li>
                            <li style={{ marginBottom: '0.5rem' }}>10m for Regulars</li>
                            <li>Fairness limit: 3 skips</li>
                        </ul>
                    </section>
                </aside>
            </div>
        </div>
    );
};

export default Orders;
