import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { AlertCircle, User, MessageSquare, Clock, Send, ShieldAlert } from 'lucide-react';

const API_BASE = 'http://localhost:8080/api';

const Complaints = ({ baristas }) => {
    const [complaints, setComplaints] = useState([]);
    const [loading, setLoading] = useState(true);
    const [newComplaint, setNewComplaint] = useState({
        baristaName: baristas.length > 0 ? baristas[0].name : '',
        customerName: '',
        message: ''
    });

    const fetchComplaints = async () => {
        try {
            const res = await axios.get(`${API_BASE}/complaints`);
            setComplaints(res.data);
            setLoading(false);
        } catch (error) {
            console.error("Error fetching complaints:", error);
        }
    };

    useEffect(() => {
        fetchComplaints();
        const interval = setInterval(fetchComplaints, 3000);
        return () => clearInterval(interval);
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`${API_BASE}/complaints/manual`, newComplaint);
            setNewComplaint({ ...newComplaint, customerName: '', message: '' });
            fetchComplaints();
        } catch (error) {
            alert("Failed to submit complaint");
        }
    };

    return (
        <div className="container">
            <header style={{ marginBottom: '2rem' }}>
                <h1 className="title">Managerial Oversight</h1>
                <p className="subtitle">Track automated delay flags and manual performance concerns</p>
            </header>

            <div style={{ display: 'grid', gridTemplateColumns: '400px 1fr', gap: '2rem' }}>
                <section>
                    <div className="card" style={{ background: '#2d1b15', color: 'white' }}>
                        <h2 style={{ fontSize: '1.2rem', marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                            <Send size={18} /> Raise Concern
                        </h2>
                        <form onSubmit={handleSubmit}>
                            <div style={{ marginBottom: '1.2rem' }}>
                                <label style={{ display: 'block', fontSize: '0.8rem', fontWeight: 600, opacity: 0.7, marginBottom: '0.4rem' }}>Target Barista</label>
                                <select
                                    value={newComplaint.baristaName}
                                    onChange={(e) => setNewComplaint({ ...newComplaint, baristaName: e.target.value })}
                                    style={{ width: '100%', padding: '0.75rem', borderRadius: '10px', border: 'none', background: 'rgba(255,255,255,0.1)', color: 'white' }}
                                >
                                    {baristas.map(b => <option key={b.id} value={b.name} style={{ color: 'black' }}>{b.name}</option>)}
                                </select>
                            </div>
                            <div style={{ marginBottom: '1.2rem' }}>
                                <label style={{ display: 'block', fontSize: '0.8rem', fontWeight: 600, opacity: 0.7, marginBottom: '0.4rem' }}>Your Name (Optional)</label>
                                <input
                                    type="text"
                                    placeholder="Guest Name"
                                    value={newComplaint.customerName}
                                    onChange={(e) => setNewComplaint({ ...newComplaint, customerName: e.target.value })}
                                    style={{ width: '100%', padding: '0.75rem', borderRadius: '10px', border: 'none', background: 'rgba(255,255,255,0.1)', color: 'white' }}
                                />
                            </div>
                            <div style={{ marginBottom: '1.5rem' }}>
                                <label style={{ display: 'block', fontSize: '0.8rem', fontWeight: 600, opacity: 0.7, marginBottom: '0.4rem' }}>Feedback Message</label>
                                <textarea
                                    rows="4"
                                    placeholder="Describe the performance issue..."
                                    value={newComplaint.message}
                                    onChange={(e) => setNewComplaint({ ...newComplaint, message: e.target.value })}
                                    style={{ width: '100%', padding: '0.75rem', borderRadius: '10px', border: 'none', background: 'rgba(255,255,255,0.1)', color: 'white', resize: 'none' }}
                                />
                            </div>
                            <button className="primary" style={{ width: '100%', background: 'var(--accent)', color: 'var(--primary)' }}>
                                Submit Feedback
                            </button>
                        </form>
                    </div>
                </section>

                <section className="card">
                    <h2 style={{ fontSize: '1.2rem', marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                        <ShieldAlert size={20} /> Complaint History
                    </h2>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                        {complaints.length === 0 ? (
                            <div style={{ textAlign: 'center', padding: '4rem', opacity: 0.4 }}>
                                <ShieldAlert size={48} style={{ margin: '0 auto 1rem' }} />
                                No complaints recorded
                            </div>
                        ) : (
                            complaints.map(c => (
                                <div key={c.id} style={{
                                    padding: '1.25rem',
                                    borderRadius: '15px',
                                    background: c.type === 'DELAYED' ? '#fff5f5' : '#f8f9fa',
                                    border: `1px solid ${c.type === 'DELAYED' ? '#feb2b2' : '#e2e8f0'}`,
                                    position: 'relative',
                                    transition: 'transform 0.2s'
                                }} className="nav-item-hover">
                                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                                            <span className={`badge ${c.type === 'DELAYED' ? 'badge-danger' : 'badge-gold'}`} style={{ fontSize: '0.6rem' }}>
                                                {c.type}
                                            </span>
                                            <span style={{ fontWeight: 800, fontSize: '0.9rem' }}>Against: {c.baristaName}</span>
                                        </div>
                                        <span style={{ fontSize: '0.7rem', opacity: 0.4, fontWeight: 600 }}>
                                            {new Date(c.timestamp).toLocaleTimeString()}
                                        </span>
                                    </div>
                                    <p style={{ margin: '0.5rem 0', fontSize: '0.85rem', fontWeight: 500, lineHeight: 1.4 }}>
                                        {c.message}
                                    </p>
                                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginTop: '0.75rem', fontSize: '0.7rem', opacity: 0.6, fontWeight: 600 }}>
                                        <div style={{ display: 'flex', alignItems: 'center', gap: '0.25rem' }}>
                                            <User size={12} /> {c.customerName || 'Anonymous'}
                                        </div>
                                        {c.orderId && (
                                            <div style={{ display: 'flex', alignItems: 'center', gap: '0.25rem' }}>
                                                <MessageSquare size={12} /> Order #{c.orderId}
                                            </div>
                                        )}
                                    </div>
                                </div>
                            ))
                        )}
                    </div>
                </section>
            </div>
        </div>
    );
};

export default Complaints;
