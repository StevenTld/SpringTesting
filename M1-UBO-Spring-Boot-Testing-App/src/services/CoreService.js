import axios from 'axios';

const API_URL = 'http://localhost:8080';

class CoreService {
  // Get student information (aggregated data)
  async getStudentInfo(id) {
    try {
      const response = await axios.get(`${API_URL}/core/student-info/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching aggregated info for student with ID ${id}:`, error);
      throw error;
    }
  }
}

export default new CoreService();
