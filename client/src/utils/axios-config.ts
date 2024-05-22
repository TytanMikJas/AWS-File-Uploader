import axios from 'axios';

export const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/file-api',
  headers: {
    'Content-Type': 'application/json',
  },
});
