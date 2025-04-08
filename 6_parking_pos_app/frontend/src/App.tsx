import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useAuthStore } from './services/auth';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import ParkingManagement from './pages/ParkingManagement';
import MemberManagement from './pages/MemberManagement';
import Reports from './pages/Reports';
import Layout from './components/Layout';

function App() {
  const user = useAuthStore((state) => state.user);

  if (!user) {
    return <Login />;
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Dashboard />} />
          <Route path="parking" element={<ParkingManagement />} />
          {user.role === 'ADMIN' && (
            <Route path="members" element={<MemberManagement />} />
          )}
          {(user.role === 'ADMIN' || user.role === 'MANAGER') && (
            <Route path="reports" element={<Reports />} />
          )}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;