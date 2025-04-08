import React, { useState, useEffect } from 'react';
import { format } from 'date-fns';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
} from 'recharts';
import { Calendar, TrendingUp, PieChart as PieChartIcon } from 'lucide-react';
import api from '../services/api';
import type { DailyRevenueDTO, OccupancyReportDTO, VehicleTypeDistributionDTO } from '../types';

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const Reports: React.FC = () => {
  const [startDate, setStartDate] = useState(format(new Date().setDate(1), 'yyyy-MM-dd'));
  const [endDate, setEndDate] = useState(format(new Date(), 'yyyy-MM-dd'));
  const [revenueData, setRevenueData] = useState<DailyRevenueDTO[]>([]);
  const [occupancyData, setOccupancyData] = useState<OccupancyReportDTO | null>(null);
  const [vehicleDistribution, setVehicleDistribution] = useState<VehicleTypeDistributionDTO[]>([]);

  useEffect(() => {
    fetchRevenueData();
    fetchOccupancyData();
    fetchVehicleDistribution();
  }, [startDate, endDate]);

  const fetchRevenueData = async () => {
    try {
      const response = await api.get(`/reports/revenue/daily?startDate=${startDate}&endDate=${endDate}`);
      setRevenueData(response.data);
    } catch (error) {
      console.error('Failed to fetch revenue data:', error);
    }
  };

  const fetchOccupancyData = async () => {
    try {
      const response = await api.get('/reports/occupancy/current');
      setOccupancyData(response.data);
    } catch (error) {
      console.error('Failed to fetch occupancy data:', error);
    }
  };

  const fetchVehicleDistribution = async () => {
    try {
      const response = await api.get(`/reports/vehicle-types?date=${format(new Date(), 'yyyy-MM-dd')}`);
      setVehicleDistribution(response.data);
    } catch (error) {
      console.error('Failed to fetch vehicle distribution:', error);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Reports & Statistics</h1>

      {/* Date Range Selector */}
      <div className="bg-white rounded-lg shadow p-6 mb-6">
        <div className="flex items-center mb-4">
          <Calendar className="h-5 w-5 text-indigo-600 mr-2" />
          <h2 className="text-lg font-semibold">Date Range</h2>
        </div>
        <div className="flex gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Start Date</label>
            <input
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              className="mt-1 block rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">End Date</label>
            <input
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              className="mt-1 block rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
            />
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Revenue Chart */}
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center mb-4">
            <TrendingUp className="h-5 w-5 text-indigo-600 mr-2" />
            <h2 className="text-lg font-semibold">Daily Revenue</h2>
          </div>
          <div className="h-80">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={revenueData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="totalRevenue" stroke="#8884d8" name="Revenue" />
                <Line type="monotone" dataKey="ticketCount" stroke="#82ca9d" name="Tickets" />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Vehicle Distribution */}
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center mb-4">
            <PieChartIcon className="h-5 w-5 text-indigo-600 mr-2" />
            <h2 className="text-lg font-semibold">Vehicle Distribution</h2>
          </div>
          <div className="h-80">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={vehicleDistribution}
                  dataKey="count"
                  nameKey="vehicleType"
                  cx="50%"
                  cy="50%"
                  outerRadius={80}
                  label
                >
                  {vehicleDistribution.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>

      {/* Current Occupancy */}
      {occupancyData && (
        <div className="mt-6 bg-white rounded-lg shadow p-6">
          <h2 className="text-lg font-semibold mb-4">Current Occupancy</h2>
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="p-4 bg-blue-50 rounded-lg">
              <p className="text-sm text-blue-600">Total Spaces</p>
              <p className="text-2xl font-bold text-blue-900">{occupancyData.totalSpaces}</p>
            </div>
            <div className="p-4 bg-green-50 rounded-lg">
              <p className="text-sm text-green-600">Available Spaces</p>
              <p className="text-2xl font-bold text-green-900">{occupancyData.availableSpaces}</p>
            </div>
            <div className="p-4 bg-yellow-50 rounded-lg">
              <p className="text-sm text-yellow-600">Occupied Spaces</p>
              <p className="text-2xl font-bold text-yellow-900">{occupancyData.occupiedSpaces}</p>
            </div>
            <div className="p-4 bg-purple-50 rounded-lg">
              <p className="text-sm text-purple-600">Occupancy Rate</p>
              <p className="text-2xl font-bold text-purple-900">{occupancyData.occupancyRate}%</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Reports;