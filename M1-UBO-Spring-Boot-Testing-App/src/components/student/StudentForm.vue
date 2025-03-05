<template>
  <div class="student-form">
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="firstName">First Name *</label>
        <input
          id="firstName"
          v-model="form.firstName"
          type="text"
          required
          :class="{ 'is-invalid': errors.firstName }"
        />
        <div v-if="errors.firstName" class="error-message">{{ errors.firstName }}</div>
      </div>

      <div class="form-group">
        <label for="lastName">Last Name *</label>
        <input
          id="lastName"
          v-model="form.lastName"
          type="text"
          required
          :class="{ 'is-invalid': errors.lastName }"
        />
        <div v-if="errors.lastName" class="error-message">{{ errors.lastName }}</div>
      </div>

      <div class="form-group">
        <label for="mail">Email *</label>
        <input
          id="mail"
          v-model="form.mail"
          type="email"
          required
          :class="{ 'is-invalid': errors.mail }"
        />
        <div v-if="errors.mail" class="error-message">{{ errors.mail }}</div>
      </div>

      <div class="form-group">
        <label for="birthDate">Birth Date</label>
        <input
          id="birthDate"
          v-model="form.birthDate"
          type="date"
          :class="{ 'is-invalid': errors.birthDate }"
        />
        <div v-if="errors.birthDate" class="error-message">{{ errors.birthDate }}</div>
      </div>

      <div class="form-group">
        <label for="academicYearId">Academic Year</label>
        <select
          id="academicYearId"
          v-model="form.academicYearId"
          :class="{ 'is-invalid': errors.academicYearId }"
        >
          <option value="">Select Academic Year</option>
          <option v-for="year in academicYears" :key="year.id" :value="year.id">
            {{ year.formationName }}
          </option>
        </select>
        <div v-if="errors.academicYearId" class="error-message">{{ errors.academicYearId }}</div>
      </div>

      <div class="form-actions">
        <button type="button" class="cancel-button" @click="cancel">Cancel</button>
        <button type="submit" class="submit-button" :disabled="isSubmitting">
          {{ isSubmitting ? 'Saving...' : submitLabel }}
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import AcademicYearService from '@/services/AcademicYearService.js';

export default {
  name: 'StudentForm',
  props: {
    initialStudent: {
      type: Object,
      default: () => ({
        firstName: '',
        lastName: '',
        mail: '',
        birthDate: '',
        academicYearId: ''
      })
    },
    submitLabel: {
      type: String,
      default: 'Save'
    }
  },
  data() {
    return {
      form: {
        firstName: this.initialStudent.firstName || '',
        lastName: this.initialStudent.lastName || '',
        mail: this.initialStudent.mail || '',
        birthDate: this.initialStudent.birthDate || '',
        academicYearId: this.initialStudent.academicYearId || ''
      },
      academicYears: [],
      errors: {},
      isSubmitting: false
    };
  },
  created() {
    this.fetchAcademicYears();
  },
  methods: {
    async fetchAcademicYears() {
      try {
        this.academicYears = await AcademicYearService.getAllAcademicYears();
      } catch (error) {
        console.error('Failed to load academic years:', error);
      }
    },
    validateForm() {
      this.errors = {};
      
      if (!this.form.firstName.trim()) {
        this.errors.firstName = 'First name is required';
      }
      
      if (!this.form.lastName.trim()) {
        this.errors.lastName = 'Last name is required';
      }
      
      if (!this.form.mail.trim()) {
        this.errors.mail = 'Email is required';
      } else if (!this.isValidEmail(this.form.mail)) {
        this.errors.mail = 'Please enter a valid email address';
      }
      
      return Object.keys(this.errors).length === 0;
    },
    isValidEmail(email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    },
    submitForm() {
      if (!this.validateForm()) return;
      
      this.isSubmitting = true;
      const formData = {
        ...this.form,
        // If studentNumber exists in initialStudent, include it to maintain the ID when updating
        ...(this.initialStudent.studentNumber && { studentNumber: this.initialStudent.studentNumber })
      };
      
      this.$emit('submit', formData);
    },
    cancel() {
      this.$emit('cancel');
    }
  },
  watch: {
    initialStudent: {
      handler(newValue) {
        this.form = {
          firstName: newValue.firstName || '',
          lastName: newValue.lastName || '',
          mail: newValue.mail || '',
          birthDate: newValue.birthDate || '',
          academicYearId: newValue.academicYearId || ''
        };
      },
      deep: true
    }
  }
};
</script>

<style scoped>
.student-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
}

input, select {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

input.is-invalid, select.is-invalid {
  border-color: #dc3545;
}

.error-message {
  color: #dc3545;
  font-size: 0.875rem;
  margin-top: 5px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}

.cancel-button, .submit-button {
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  border: none;
  transition: background-color 0.3s;
}

.cancel-button {
  background-color: #6c757d;
  color: white;
}

.cancel-button:hover {
  background-color: #5a6268;
}

.submit-button {
  background-color: #28a745;
  color: white;
}

.submit-button:hover:not(:disabled) {
  background-color: #218838;
}

.submit-button:disabled {
  background-color: #6c757d;
  cursor: not-allowed;
}
</style>
