import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import axios from 'axios';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import Orders from './pages/Orders';
import Staff from './pages/Staff';
import Simulation from './pages/Simulation';
import Complaints from './pages/Complaints';

const API_BASE = 'http://localhost:8080/api';

const App = () => {
    const [queue, setQueue] = useState([]);
    const [baristas, setBaristas] = useState([]);
    const [isSimulating, setIsSimulating] = useState(false);
    const [loading, setLoading] = useState(true);
    const [newOrder, setNewOrder] = useState({
        customerName: '',
        customerType: 'NEW',
        loyaltyStatus: 'NONE',
        drinkType: 'LATTE'
    });

    const fetchData = async () => {
        try {
            const [ordersRes, baristaRes, simulateRes] = await Promise.all([
                axios.get(`${API_BASE}/orders/all`),
                axios.get(`${API_BASE}/baristas`),
                axios.get(`${API_BASE}/simulate/status`)
            ]);
            setQueue(ordersRes.data);
            setBaristas(baristaRes.data);
            setIsSimulating(simulateRes.data.isSimulating);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    useEffect(() => {
        fetchData();
        const interval = setInterval(fetchData, 2000);
        return () => clearInterval(interval);
    }, []);

    const handleCreateOrder = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`${API_BASE}/orders`, newOrder);
            setNewOrder({ ...newOrder, customerName: '', customerType: 'NEW', loyaltyStatus: 'NONE', drinkType: 'LATTE' });
            fetchData();
        } catch (error) {
            alert('Error creating order');
        }
    };

    const toggleSimulation = async () => {
        try {
            const action = isSimulating ? 'stop' : 'start';
            await axios.post(`${API_BASE}/simulate/${action}`);
            setIsSimulating(!isSimulating);
        } catch (error) {
            alert('Error toggling simulation');
        }
    };

    if (loading) {
        return (
            <div style={{ height: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', background: 'var(--background)' }}>
                <div style={{ textAlign: 'center' }}>
                    <div className="badge badge-gold" style={{ padding: '1rem 2rem', fontSize: '1.2rem' }}>Brewing Excellence...</div>
                </div>
            </div>
        );
    }

    return (
        <BrowserRouter>
            <div className="app-layout">
                <Navbar />
                <main className="main-content">
                    <Routes>
                        <Route path="/" element={<Dashboard queue={queue} baristas={baristas} />} />
                        <Route path="/orders" element={
                            <Orders
                                queue={queue}
                                isSimulating={isSimulating}
                                toggleSimulation={toggleSimulation}
                                fetchData={fetchData}
                            />
                        } />
                        <Route path="/staff" element={
                            <Staff
                                baristas={baristas}
                                queue={queue}
                                newOrder={newOrder}
                                setNewOrder={setNewOrder}
                                handleCreateOrder={handleCreateOrder}
                            />
                        } />
                        <Route path="/simulation" element={<Simulation />} />
                        <Route path="/complaints" element={<Complaints baristas={baristas} />} />
                        <Route path="*" element={<Navigate to="/" />} />
                    </Routes>
                </main>
            </div>
        </BrowserRouter>
    );
};

export default App;
