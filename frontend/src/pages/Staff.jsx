import React from 'react';
import { Users, Coffee, RefreshCcw, Info, TrendingUp, Award, CheckCircle } from 'lucide-react';

const Staff = ({ baristas, queue, newOrder, setNewOrder, handleCreateOrder }) => {
    return (
        <div className="container">
            <header style={{ marginBottom: '2rem' }}>
                <h1>Staff & Tasks</h1>
                <p>Schedules, manual entries & performance</p>
            </header>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 350px', gap: '2rem' }}>
                <section>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
                        <h2 style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', margin: 0 }}>
                            <Users size={18} /> Barista Performance
                        </h2>
                        <div style={{ padding: '0.5rem 1rem', background: '#fdf6e3', border: '1px solid #eee8d5', borderRadius: '8px', fontSize: '0.75rem', color: '#657b83' }}>
                            <Info size={12} style={{ verticalAlign: 'middle', marginRight: 4 }} />
                            <b>100% Index:</b> Peak squad performance. Others are relative.
                        </div>
                    </div>

                    <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
                        {baristas.map(barista => {
                            const recentlyCompleted = queue
                                .filter(o => o.assignedBaristaName === barista.name && o.status === 'COMPLETED')
                                .slice(0, 3);

                            return (
                                <div key={barista.id} className="card barista-performance-card" style={{ padding: '2rem' }}>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                                        <div style={{ display: 'flex', gap: '1.5rem' }}>
                                            <div style={{ width: '60px', height: '60px', borderRadius: '50%', background: 'var(--primary)', color: 'white', display: 'flex', alignItems: 'center', justifyContent: 'center', fontWeight: 800, fontSize: '1.5rem' }}>
                                                {barista.name.charAt(barista.name.length - 1)}
                                            </div>
                                            <div>
                                                <h3 style={{ margin: 0, fontSize: '1.2rem' }}>{barista.name}</h3>
                                                <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginTop: '0.25rem' }}>
                                                    <span className={`badge badge-${barista.available ? 'success' : 'warning'}`} style={{ fontSize: '0.65rem' }}>
                                                        {barista.available ? 'AVAILABLE' : 'PREPARING'}
                                                    </span>
                                                    <span style={{ fontSize: '0.75rem', opacity: 0.6 }}>Exp: {barista.totalWorkTime}m</span>
                                                </div>
                                            </div>
                                        </div>

                                        <div style={{ textAlign: 'right' }}>
                                            <div style={{ fontSize: '2rem', fontWeight: 800, color: 'var(--primary)', lineHeight: 1 }}>
                                                {Math.round(barista.currentLoad * 100)}%
                                            </div>
                                            <div style={{ fontSize: '0.65rem', fontWeight: 700, opacity: 0.5, letterSpacing: '0.05em' }}>PRODUCTIVITY INDEX</div>
                                        </div>
                                    </div>

                                    <div style={{ marginTop: '2rem', display: 'grid', gridTemplateColumns: 'minmax(0, 1fr) minmax(0, 1.5fr)', gap: '2rem', borderTop: '1px solid #eee', paddingTop: '1.5rem' }}>
                                        <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                                            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                                                <div style={{ padding: '0.5rem', background: '#f0f4f8', borderRadius: '8px' }}><Coffee size={16} color="#4a5568" /></div>
                                                <div>
                                                    <div style={{ fontSize: '1rem', fontWeight: 800 }}>{barista.totalDrinksPrepared || 0}</div>
                                                    <div style={{ fontSize: '0.65rem', opacity: 0.5 }}>DRINKS TODAY</div>
                                                </div>
                                            </div>
                                            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                                                <div style={{ padding: '0.5rem', background: '#fff5f5', borderRadius: '8px' }}><Award size={16} color="#c53030" /></div>
                                                <div>
                                                    <div style={{ fontSize: '1rem', fontWeight: 800 }}>{barista.currentLoad >= 0.9 ? 'ELITE' : barista.currentLoad >= 0.7 ? 'A+' : 'ACTIVE'}</div>
                                                    <div style={{ fontSize: '0.65rem', opacity: 0.5 }}>SKILL RATING</div>
                                                </div>
                                            </div>
                                        </div>

                                        <div>
                                            <div style={{ fontSize: '0.7rem', fontWeight: 800, color: '#aaa', marginBottom: '0.75rem', letterSpacing: '0.05em' }}>RECENTLY COMPLETED</div>
                                            {recentlyCompleted.length === 0 ? (
                                                <div style={{ fontSize: '0.75rem', opacity: 0.4, fontStyle: 'italic' }}>No drinks completed yet</div>
                                            ) : (
                                                <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                                                    {recentlyCompleted.map(order => (
                                                        <div key={order.id} style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.8rem' }}>
                                                            <CheckCircle size={12} color="var(--success)" />
                                                            <b>{order.drinkType}</b>
                                                            <span style={{ opacity: 0.5 }}>• {order.customerName}</span>
                                                        </div>
                                                    ))}
                                                </div>
                                            )}
                                        </div>
                                    </div>

                                    {!barista.available && barista.remainingPrepSeconds !== null && !isNaN(barista.remainingPrepSeconds) && (
                                        <div style={{ marginTop: '1.5rem', padding: '1rem', background: '#fff9db', borderRadius: '8px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                            <div style={{ fontSize: '0.8rem', fontWeight: 700 }}>Preparing {barista.currentDrink}</div>
                                            <div className="wait-timer" style={{ background: 'var(--primary)', color: 'white', padding: '0.2rem 0.6rem', borderRadius: '4px' }}>
                                                {Math.floor(barista.remainingPrepSeconds / 60)}:{String(barista.remainingPrepSeconds % 60).padStart(2, '0')}
                                            </div>
                                        </div>
                                    )}
                                </div>
                            );
                        })}
                    </div>
                </section>

                <aside className="card" style={{ height: 'fit-content', border: '1px solid rgba(0,0,0,0.05)', boxShadow: '0 8px 32px rgba(0,0,0,0.04)' }}>
                    <h2 style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', marginBottom: '1.5rem', color: '#4e342e' }}>
                        <div style={{ background: '#efebe9', padding: '0.5rem', borderRadius: '10px', display: 'flex' }}>
                            <Coffee size={20} color="#4e342e" />
                        </div>
                        New Order
                    </h2>
                    <form onSubmit={handleCreateOrder}>
                        <div style={{ marginBottom: '1.25rem' }}>
                            <label className="stat-label" style={{ marginBottom: '0.4rem', display: 'block', fontSize: '0.7rem', color: '#8d6e63', fontWeight: 700 }}>CUSTOMER NAME</label>
                            <input
                                type="text"
                                placeholder="e.g. John Doe"
                                value={newOrder.customerName}
                                onChange={(e) => setNewOrder({ ...newOrder, customerName: e.target.value })}
                                required
                                style={{ background: '#fafafa', border: '1.5px solid #eceff1', borderRadius: '10px', padding: '0.8rem 1rem', width: '100%', fontSize: '0.9rem' }}
                            />
                        </div>

                        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem', marginBottom: '1.25rem' }}>
                            <div>
                                <label className="stat-label" style={{ marginBottom: '0.4rem', display: 'block', fontSize: '0.7rem', color: '#8d6e63', fontWeight: 700 }}>TYPE</label>
                                <select
                                    value={newOrder.customerType}
                                    onChange={(e) => setNewOrder({ ...newOrder, customerType: e.target.value })}
                                    style={{ background: '#fafafa', border: '1.5px solid #eceff1', borderRadius: '10px', padding: '0.8rem', width: '100%', fontSize: '0.9rem' }}
                                >
                                    <option value="NEW">New</option>
                                    <option value="REGULAR">Regular</option>
                                </select>
                            </div>
                            <div>
                                <label className="stat-label" style={{ marginBottom: '0.4rem', display: 'block', fontSize: '0.7rem', color: '#8d6e63', fontWeight: 700 }}>LOYALTY</label>
                                <select
                                    value={newOrder.loyaltyStatus}
                                    onChange={(e) => setNewOrder({ ...newOrder, loyaltyStatus: e.target.value })}
                                    style={{ background: '#fafafa', border: '1.5px solid #eceff1', borderRadius: '10px', padding: '0.8rem', width: '100%', fontSize: '0.9rem' }}
                                >
                                    <option value="NONE">None</option>
                                    <option value="BRONZE">Bronze</option>
                                    <option value="SILVER">Silver</option>
                                    <option value="GOLD">Gold</option>
                                </select>
                            </div>
                        </div>

                        <div style={{ marginBottom: '1.75rem' }}>
                            <label className="stat-label" style={{ marginBottom: '0.4rem', display: 'block', fontSize: '0.7rem', color: '#8d6e63', fontWeight: 700 }}>DRINK SELECTION</label>
                            <select
                                value={newOrder.drinkType}
                                onChange={(e) => setNewOrder({ ...newOrder, drinkType: e.target.value })}
                                style={{ background: '#fafafa', border: '1.5px solid #eceff1', borderRadius: '10px', padding: '0.8rem', width: '100%', fontSize: '0.9rem' }}
                            >
                                <option value="LATTE">Latte (4m)</option>
                                <option value="ESPRESSO">Espresso (2m)</option>
                                <option value="COLD_BREW">Cold Brew (1m)</option>
                                <option value="AMERICANO">Americano (2m)</option>
                                <option value="CAPPUCCINO">Cappuccino (3m)</option>
                                <option value="MOCHA">Mocha (5m)</option>
                            </select>
                        </div>

                        <button
                            type="submit"
                            className="btn-primary"
                            style={{
                                width: '100%',
                                padding: '1rem',
                                background: '#4e342e',
                                border: 'none',
                                borderRadius: '12px',
                                color: 'white',
                                fontWeight: 800,
                                cursor: 'pointer',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                gap: '0.75rem',
                                transition: 'transform 0.2s cubic-bezier(0.34, 1.56, 0.64, 1)',
                                boxShadow: '0 4px 12px rgba(78, 52, 46, 0.2)'
                            }}
                            onMouseOver={(e) => e.currentTarget.style.transform = 'translateY(-2px)'}
                            onMouseOut={(e) => e.currentTarget.style.transform = 'translateY(0)'}
                        >
                            <RefreshCcw size={18} /> Place Order
                        </button>
                    </form>
                </aside>
            </div>
        </div>
    );
};

export default Staff;
