import apiClient from './apiClient';

const API_BASE_URL = 'http://localhost:8080/api/matches';

export const matchService = {
    getAllMatches: async (matchType = '') => {
        console.log("Fetching matches with matchType:", matchType);
        const response = await apiClient.get(API_BASE_URL, {
            params: { 
                matchType : matchType ? matchType.trim() : undefined 
            }
        });
        console.log("Received matches data:", response.data);
        return response.data;
    },

    getMatchById: async (id) => {
        const response = await apiClient.get(`${API_BASE_URL}/${id}`);
        return response.data;
    },

    createMatch: async (matchData) => {
        const response = await apiClient.post(API_BASE_URL, matchData);
        return response.data;
    },

    updateMatch: async (id, matchData) => {
        const response = await apiClient.put(`${API_BASE_URL}/${id}`, matchData);
        return response.data;
    },

    deleteMatch: async (id) => {
        const response = await apiClient.delete(`${API_BASE_URL}/${id}`);
        return response.data;
    }




}