import axios from 'axios'

// Si votre application Spring Boot tourne sur le port 8080
const API_URL = 'http://localhost:8080/api/academicyears'

class AcademicYearService {
    async getAllYears() {
        const response = await axios.get(API_URL)
        return response.data
    }

    async getYearById(id) {
        const response = await axios.get(`${API_URL}/${id}`)
        return response.data
    }

    async createYear(year) {
        const response = await axios.post(API_URL, year)
        return response.data
    }

    async updateYear(year) {
        const response = await axios.put(`${API_URL}/${year.id}`, year)
        return response.data
    }

    async deleteYear(id) {
        const response = await axios.delete(`${API_URL}/${id}`)
        return response.data
    }
}

export default new AcademicYearService()