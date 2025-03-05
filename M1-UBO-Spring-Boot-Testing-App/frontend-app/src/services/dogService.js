import axios from 'axios'

// Si votre application Spring Boot tourne sur le port 8080
const API_URL = 'http://localhost:8080/api/dogs'

class DogService {
    async getAllDogs() {
        const response = await axios.get(API_URL)
        return response.data
    }

    async getDogById(id) {
        const response = await axios.get(`${API_URL}/${id}`)
        return response.data
    }

    async createDog(dog) {
        const response = await axios.post(API_URL, dog)
        return response.data
    }

    async updateDog(dog) {
        const response = await axios.put(`${API_URL}/${dog.id}`, dog)
        return response.data
    }

    async deleteDog(id) {
        const response = await axios.delete(`${API_URL}/${id}`)
        return response.data
    }
}

export default new DogService()