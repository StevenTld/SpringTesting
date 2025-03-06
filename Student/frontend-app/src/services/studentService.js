import axios from 'axios'

// Si votre application Spring Boot tourne sur le port 8080
const API_URL = 'http://localhost:8080/api/students'

class StudentService {
    // Opérations de base CRUD
    async getAllStudents() {
        const response = await axios.get(API_URL)
        return response.data
    }

    async getStudentById(id) {
        const response = await axios.get(`${API_URL}/${id}`)
        return response.data
    }

    async createStudent(student) {
        const response = await axios.post(API_URL, student)
        return response.data
    }

    async updateStudent(id, student) {
        const response = await axios.put(`${API_URL}/${id}`, student)
        return response.data
    }

    async deleteStudent(id) {
        const response = await axios.delete(`${API_URL}/${id}`)
        return response.data
    }

    // Opérations liées aux groupes
    async getStudentGroups(id) {
        const response = await axios.get(`${API_URL}/${id}/groupes`)
        return response.data
    }

    async addStudentToGroup(studentId, groupId) {
        const response = await axios.post(`${API_URL}/${studentId}/groupes/${groupId}`)
        return response.data
    }

    async removeStudentFromGroup(studentId, groupId) {
        const response = await axios.delete(`${API_URL}/${studentId}/groupes/${groupId}`)
        return response.data
    }

    async validateStudentGroup(studentId, groupId, isAccepted) {
        const response = await axios.put(`${API_URL}/${studentId}/groupes/${groupId}`, { accept: isAccepted })
        return response.data
    }

    // Opérations liées aux formations
    async getStudentFormations(id) {
        const response = await axios.get(`${API_URL}/${id}/formations`)
        return response.data
    }

    async addStudentToFormation(studentId, formationId) {
        const response = await axios.post(`${API_URL}/${studentId}/formations/${formationId}`)
        return response.data
    }

    async removeStudentFromFormation(studentId, formationId) {
        const response = await axios.delete(`${API_URL}/${studentId}/formations/${formationId}`)
        return response.data
    }

    async validateStudentFormation(studentId, formationId, isAccepted) {
        const response = await axios.put(`${API_URL}/${studentId}/formations/${formationId}`, { accept: isAccepted })
        return response.data
    }

    // Opérations liées aux UEs
    async getStudentModules(id) {
        const response = await axios.get(`${API_URL}/${id}/ues`)
        return response.data
    }

    async addStudentToUE(studentId, ueId) {
        const response = await axios.post(`${API_URL}/${studentId}/ues/${ueId}`)
        return response.data
    }

    async removeStudentFromUE(studentId, ueId) {
        const response = await axios.delete(`${API_URL}/${studentId}/ues/${ueId}`)
        return response.data
    }

    async validateStudentUE(studentId, ueId, isAccepted) {
        const response = await axios.put(`${API_URL}/${studentId}/ues/${ueId}`, { accept: isAccepted })
        return response.data
    }

    // Accès à l'API Core (information agrégée)
    async getStudentInfo(id) {
        const response = await axios.get(`http://localhost:8080/api/core/student-info/${id}`)
        return response.data
    }
}

export default new StudentService()