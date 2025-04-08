import { create } from 'zustand';
import { User } from '../types';

interface AuthState {
  user: User | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  login: async (username: string, password: string) => {
    localStorage.setItem('username', username);
    localStorage.setItem('password', password);
    
    // Map roles based on username as per your SecurityConfig
    let role: User['role'] = 'OPERATOR';
    if (username === 'admin') role = 'ADMIN';
    if (username === 'manager') role = 'MANAGER';
    
    set({ user: { username, role } });
  },
  logout: () => {
    localStorage.removeItem('username');
    localStorage.removeItem('password');
    set({ user: null });
  },
}));