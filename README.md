# Smart Coffee Shop Barista Queue System

A full-stack application for Bean & Brew café designed to optimize coffee preparation queues during rush hours.

## Tech Stack
- **Frontend**: React (Vite, Hooks, Axios)
- **Backend**: Spring Boot, Java JPA, Hibernate
- **Database**: MySQL
- **Scheduling**: Spring @Scheduled

## The Algorithm: Dynamic Priority Queue (DPQ)

Currently, the café uses FIFO, which makes customers with simple orders wait behind complex ones. Our DPQ algorithm improves efficiency and fairness.

### Priority Scoring Formula (0-100+)
Each order is scored every 30 seconds based on:
1. **Wait Time (40%)**: Score increases linearly up to 10 minutes.
2. **Order Complexity (25%)**: Shorter drinks (e.g., Cold Brew) get a higher score to "fast-track" them.
3. **Loyalty Status (10%)**: Gold and Silver members get a fixed priority boost.
4. **Urgency (25%)**: Exponential increase as the customer approaches the 8-minute abandonment mark.

### Key Rules
- **Emergency Boost**: If wait time > 8 minutes, a +50 boost is applied to ensure the order is picked up immediately.
- **Workload Balancing**: Baristas with load > 1.2x the average are assigned simpler orders (<= 2m) to prevent bottlenecks.
- **Fairness Tracking**: If an order is skipped by more than 3 later arrivals, it receives a fairness boost to prevent "infinite skipping."

## Setup Instructions

### Backend
1. Ensure MySQL is running.
2. Create a database named `smart_barista_db` (or Hibernate will auto-create it).
3. Navigate to `/backend` and run:
   ```bash
   mvn spring-boot:run
   ```

### Frontend
1. Navigate to `/frontend`.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the dev server:
   ```bash
   npm run dev
   ```

## Features
- **Live Queue**: Real-time visualization of waiting orders and their priority scores.
- **Barista Dashboard**: Monitor workload ratios and total work time for each barista.
- **Simulation**: Poisson-distributed customer arrival simulation (λ = 1.4/min).
- **Manual Control**: Add orders manually for testing specific scenarios.
