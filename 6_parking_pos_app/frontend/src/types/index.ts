export interface ParkingTicket {
  id: number;
  licensePlate: string;
  vehicleType: string;
  entryTime: string;
  exitTime?: string;
  duration?: number;
  fee?: number;
  status: 'ACTIVE' | 'COMPLETED';
}

export interface CheckInRequest {
  licensePlate: string;
  vehicleType: string;
  driverName: string;
  phoneNumber?: string;
}

export interface CheckOutRequest {
  ticketId: number;
}

export interface Payment {
  id: number;
  ticketId: number;
  amount: number;
  paymentTime: string;
  paymentMethod: string;
}

export interface Rate {
  id: number;
  vehicleType: string;
  baseRate: number;
  additionalRate: number;
  description?: string;
}

export interface Member {
  id: number;
  name: string;
  email: string;
  phoneNumber: string;
  vehiclePlate: string;
  membershipType: string;
  expiryDate: string;
  active: boolean;
}

export interface DailyRevenueDTO {
  date: string;
  totalRevenue: number;
  ticketCount: number;
}

export interface OccupancyReportDTO {
  totalSpaces: number;
  occupiedSpaces: number;
  availableSpaces: number;
  occupancyRate: number;
}

export interface VehicleTypeDistributionDTO {
  vehicleType: string;
  count: number;
  percentage: number;
}

export interface User {
  username: string;
  role: 'OPERATOR' | 'MANAGER' | 'ADMIN';
}