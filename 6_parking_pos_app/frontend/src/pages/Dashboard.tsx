import React from 'react';
import { useAuthStore } from '../services/auth';
import { BarChart3, Users, Car } from 'lucide-react';

const Dashboard = () => {
  const user = useAuthStore((state) => state.user);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Welcome back, {user?.name || 'User'}</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Parking Status Card */}
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-semibold text-gray-700">Parking Status</h2>
            <Car className="h-6 w-6 text-blue-500" />
          </div>
          <p className="text-3xl font-bold text-gray-900">85%</p>
          <p className="text-sm text-gray-500">Current occupancy</p>
        </div>

        {/* Active Members Card */}
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-semibold text-gray-700">Active Members</h2>
            <Users className="h-6 w-6 text-green-500" />
          </div>
          <p className="text-3xl font-bold text-gray-900">247</p>
          <p className="text-sm text-gray-500">Total members</p>
        </div>

        {/* Monthly Revenue Card */}
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-semibold text-gray-700">Monthly Revenue</h2>
            <BarChart3 className="h-6 w-6 text-purple-500" />
          </div>
          <p className="text-3xl font-bold text-gray-900">$12,450</p>
          <p className="text-sm text-gray-500">This month</p>
        </div>
      </div>

      {/* Recent Activity Section */}
      <div className="mt-8">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Recent Activity</h2>
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="divide-y divide-gray-200">
            {[
              { time: '2 hours ago', action: 'New member registration', user: 'John Smith' },
              { time: '4 hours ago', action: 'Parking space #A12 occupied', user: 'Sarah Johnson' },
              { time: '5 hours ago', action: 'Monthly payment received', user: 'Michael Brown' },
              { time: '1 day ago', action: 'Visitor pass issued', user: 'Emma Wilson' },
            ].map((activity, index) => (
              <div key={index} className="p-4 hover:bg-gray-50">
                <div className="flex justify-between">
                  <div>
                    <p className="text-sm font-medium text-gray-900">{activity.action}</p>
                    <p className="text-sm text-gray-500">{activity.user}</p>
                  </div>
                  <p className="text-sm text-gray-400">{activity.time}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;