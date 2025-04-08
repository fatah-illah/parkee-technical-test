import { create } from 'zustand';
import { User } from '../types';
import api from './api.ts';

interface AuthState {
  user: User | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  getAuthHeader: () => string | null;
}

function mapRole(username: string): User['role'] {
  if (username === 'admin') return 'ADMIN';
  if (username === 'manager') return 'MANAGER';
  return 'OPERATOR';
}

function getUserFromLocalStorage(): User | null {
  const username = localStorage.getItem('username');
  if (!username) return null;

  return { username, role: mapRole(username) };
}

export const useAuthStore = create<AuthState>((set) => ({
  user: getUserFromLocalStorage(),

  login: async (username: string, password: string) => {
    localStorage.setItem('username', username);
    localStorage.setItem('password', password);

    try {
      await api.get('/me');

      set({ user: { username, role: mapRole(username) } });
    } catch (error) {
      localStorage.removeItem('username');
      localStorage.removeItem('password');
      set({ user: null });
      throw error;
    }
  },

  logout: () => {
    localStorage.removeItem('username');
    localStorage.removeItem('password');
    set({ user: null });
  },

  getAuthHeader: () => {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');
    if (username && password) {
      return `Basic ${btoa(`${username}:${password}`)}`;
    }
    return null;
  },
}));