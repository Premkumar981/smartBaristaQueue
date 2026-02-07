import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, Coffee, Users, Info, Zap, AlertTriangle } from 'lucide-react';

const Navbar = () => {
    return (
        <nav className="sidebar">
            <div className="sidebar-brand">
                <Coffee size={28} />
                <span>Bean & Brew</span>
            </div>
            <div className="sidebar-links">
                <NavLink to="/" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <LayoutDashboard size={20} />
                    <span>Dashboard</span>
                </NavLink>
                <NavLink to="/orders" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <Coffee size={20} />
                    <span>Live Queue</span>
                </NavLink>
                <NavLink to="/staff" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <Users size={20} />
                    <span>Staff & Tasks</span>
                </NavLink>
                <NavLink to="/simulation" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <Zap size={20} />
                    <span>Simulation</span>
                </NavLink>
                <NavLink to="/complaints" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <AlertTriangle size={20} />
                    <span>Complaints</span>
                </NavLink>
            </div>
            <div className="sidebar-footer">
                <div className="nav-item opacity-50">
                    <Info size={16} />
                    <span style={{ fontSize: '0.7rem' }}>v2.0.0 Peak Flow</span>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
