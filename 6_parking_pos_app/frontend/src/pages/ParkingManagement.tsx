import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { Car, UserCheck, Camera } from 'lucide-react';
import api from '../services/api';
import type { ParkingTicket, CheckInRequest, CheckOutRequest } from '../types';

const checkInSchema = z.object({
  licensePlate: z.string().min(1, 'License plate is required'),
  vehicleType: z.string().min(1, 'Vehicle type is required'),
  driverName: z.string().min(1, 'Driver name is required'),
  phoneNumber: z.string().optional(),
});

const checkOutSchema = z.object({
  ticketId: z.number().min(1, 'Ticket ID is required'),
});

type CheckInForm = z.infer<typeof checkInSchema>;
type CheckOutForm = z.infer<typeof checkOutSchema>;

const ParkingManagement: React.FC = () => {
  const [activeTickets, setActiveTickets] = useState<ParkingTicket[]>([]);
  const [selectedTicket, setSelectedTicket] = useState<ParkingTicket | null>(null);
  const [entryCameraImage, setEntryCameraImage] = useState<File | null>(null);
  const [faceEntryCameraImage, setFaceEntryCameraImage] = useState<File | null>(null);

  const checkInForm = useForm<CheckInForm>({
    resolver: zodResolver(checkInSchema),
  });

  const checkOutForm = useForm<CheckOutForm>({
    resolver: zodResolver(checkOutSchema),
  });

  const handleCheckIn = async (data: CheckInForm) => {
    try {
      const response = await api.post<ParkingTicket>('/parking/check-in', data);
      
      if (entryCameraImage && faceEntryCameraImage) {
        const formData = new FormData();
        formData.append('entryCameraImage', entryCameraImage);
        formData.append('faceEntryCameraImage', faceEntryCameraImage);
        await api.post(`/camera/entry/${response.data.id}`, formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        });
      }

      setActiveTickets([...activeTickets, response.data]);
      checkInForm.reset();
      setEntryCameraImage(null);
      setFaceEntryCameraImage(null);
    } catch (error) {
      console.error('Check-in failed:', error);
    }
  };

  const handleCheckOut = async (data: CheckOutForm) => {
    try {
      await api.post('/parking/check-out', data);
      setActiveTickets(activeTickets.filter(ticket => ticket.id !== data.ticketId));
      checkOutForm.reset();
    } catch (error) {
      console.error('Check-out failed:', error);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Parking Management</h1>
      
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Check-in Form */}
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center mb-4">
            <Car className="h-5 w-5 text-indigo-600 mr-2" />
            <h2 className="text-lg font-semibold">Vehicle Check-in</h2>
          </div>
          
          <form onSubmit={checkInForm.handleSubmit(handleCheckIn)} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700">License Plate</label>
              <input
                {...checkInForm.register('licensePlate')}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
              {checkInForm.formState.errors.licensePlate && (
                <p className="mt-1 text-sm text-red-600">{checkInForm.formState.errors.licensePlate.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Vehicle Type</label>
              <select
                {...checkInForm.register('vehicleType')}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              >
                <option value="">Select type</option>
                <option value="CAR">Car</option>
                <option value="MOTORCYCLE">Motorcycle</option>
                <option value="TRUCK">Truck</option>
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Driver Name</label>
              <input
                {...checkInForm.register('driverName')}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Phone Number</label>
              <input
                {...checkInForm.register('phoneNumber')}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
            </div>

            <div className="space-y-2">
              <label className="block text-sm font-medium text-gray-700">Entry Camera Images</label>
              <div className="flex gap-4">
                <input
                  type="file"
                  accept="image/*"
                  onChange={(e) => setEntryCameraImage(e.target.files?.[0] || null)}
                  className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:text-sm file:font-semibold file:bg-indigo-50 file:text-indigo-700 hover:file:bg-indigo-100"
                />
                <input
                  type="file"
                  accept="image/*"
                  onChange={(e) => setFaceEntryCameraImage(e.target.files?.[0] || null)}
                  className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:text-sm file:font-semibold file:bg-indigo-50 file:text-indigo-700 hover:file:bg-indigo-100"
                />
              </div>
            </div>

            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              Check In
            </button>
          </form>
        </div>

        {/* Check-out Form */}
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center mb-4">
            <UserCheck className="h-5 w-5 text-green-600 mr-2" />
            <h2 className="text-lg font-semibold">Vehicle Check-out</h2>
          </div>

          <form onSubmit={checkOutForm.handleSubmit(handleCheckOut)} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700">Ticket ID</label>
              <input
                type="number"
                {...checkOutForm.register('ticketId', { valueAsNumber: true })}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
            </div>

            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
            >
              Check Out
            </button>
          </form>
        </div>
      </div>

      {/* Active Tickets List */}
      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-4">Active Tickets</h2>
        <div className="bg-white shadow overflow-hidden sm:rounded-md">
          <ul className="divide-y divide-gray-200">
            {activeTickets.map((ticket) => (
              <li key={ticket.id} className="px-6 py-4 hover:bg-gray-50">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-gray-900">
                      Ticket #{ticket.id} - {ticket.licensePlate}
                    </p>
                    <p className="text-sm text-gray-500">
                      Entry Time: {new Date(ticket.entryTime).toLocaleString()}
                    </p>
                  </div>
                  <button
                    onClick={() => setSelectedTicket(ticket)}
                    className="ml-4 text-sm font-medium text-indigo-600 hover:text-indigo-500"
                  >
                    View Details
                  </button>
                </div>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default ParkingManagement;