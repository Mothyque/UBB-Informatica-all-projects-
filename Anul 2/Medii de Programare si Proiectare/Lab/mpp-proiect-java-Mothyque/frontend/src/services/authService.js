import axios from "axios";

const API_URL = "http://localhost:8080/api/auth";

const login = async (username, password) => {
    const response = await axios.post(`${API_URL}/login`, {username: username, password: password});
    if (response.data.token) {
        localStorage.setItem('jwt', response.data.token);
    }
    return response.data;
};

const logout = () => {
    localStorage.removeItem('jwt');
};

const getToken = () => localStorage.getItem('jwt');

export default {
    login,
    logout,
    getToken
};