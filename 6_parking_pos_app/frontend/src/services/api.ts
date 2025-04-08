import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 15000,
});

api.interceptors.request.use(
    (config) => {
        const username = localStorage.getItem('username');
        const password = localStorage.getItem('password');

        if (username && password) {
            const auth = btoa(`${username}:${password}`);
            config.headers.Authorization = `Basic ${auth}`;
        }

        return config;
    },
    (error) => {
        // Handle request error
        console.error('Request error:', error);
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response?.status === 401) {
            console.log('Unauthorized access, redirecting to login');
            localStorage.removeItem('username');
            localStorage.removeItem('password');
            window.location.href = '/login';
        }

        else if (error.response?.status === 500) {
            console.error('Server error:', error.response.data);
        }

        else if (error.code === 'ECONNABORTED' || !error.response) {
            console.error('Network error or timeout');
        }

        return Promise.reject(error);
    }
);

export default api;
