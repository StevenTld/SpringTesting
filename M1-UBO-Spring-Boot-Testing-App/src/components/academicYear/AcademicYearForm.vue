<template>
  <div class="academic-year-form">
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="formationName">Formation Name *</label>
        <input
          id="formationName"
          v-model="form.formationName"
          type="text"
          required
          :class="{ 'is-invalid': errors.formationName }"
        />
        <div v-if="errors.formationName" class="error-message">{{ errors.formationName }}</div>
      </div>

      <div class="form-group">
        <label for="tpGroupSize">TP Group Size *</label>
        <input
          id="tpGroupSize"
          v-model.number="form.tpGroupSize"
          type="number"
          min="1"
          required
          :class="{ 'is-invalid': errors.tpGroupSize }"
        />
        <div v-if="errors.tpGroupSize" class="error-message">{{ errors.tpGroupSize }}</div>
      </div>

      <div class="form-group">
        <label for="tdGroupSize">TD Group Size *</label>
        <input
          id="tdGroupSize"
          v-model.number="form.tdGroupSize"
          type="number"
          min="1"
          required
          :class="{ 'is-invalid': errors.tdGroupSize }"
        />
        <div v-if="errors.tdGroupSize" class="error-message">{{ errors.tdGroupSize }}</div>
      </div>

      <div class="form-group">
        <label for="optionalTeachingUnitsCount">Optional Teaching Units Count *</label>
        <input
          id="optionalTeachingUnitsCount"
          v-model.number="form.optionalTeachingUnitsCount"
          type="number"
          min="0"
          required
          :class="{ 'is-invalid': errors.optionalTeachingUnitsCount }"
        />
        <div v-if="errors.optionalTeachingUnitsCount" class="error-message">{{ errors.optionalTeachingUnitsCount }}</div>
      </div>

      <div class="form-group">
        <label for="responsibleEmail">Responsible Email *</label>
        <input
          id="responsibleEmail"
          v-model="form.responsibleEmail"
          type="email"
          required
          :class="{ 'is-invalid': errors.responsibleEmail }"
        />
        <div v-if="errors.responsibleEmail" class="error-message">{{ errors.responsibleEmail }}</div>
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
export default {
  name: 'AcademicYearForm',
  props: {
    initialAcademicYear: {
      type: Object,
      default: () => ({
        formationName: '',
        tpGroupSize: null,
        tdGroupSize: null,
        optionalTeachingUnitsCount: null,
        responsibleEmail: ''
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
        formationName: this.initialAcademicYear.formationName || '',
        tpGroupSize: this.initialAcademicYear.tpGroupSize || null,
        tdGroupSize: this.initialAcademicYear.tdGroupSize || null,
        optionalTeachingUnitsCount: this.initialAcademicYear.optionalTeachingUnitsCount || null,
        responsibleEmail: this.initialAcademicYear.responsibleEmail || ''
      },
      errors: {},
      isSubmitting: false
    };
  },
  methods: {
    validateForm() {
      this.errors = {};
      
      if (!this.form.formationName.trim()) {
        this.errors.formationName = 'Formation name is required';
      }
      
      if (!this.form.tpGroupSize) {
        this.errors.tpGroupSize = 'TP group size is required';
      } else if (this.form.tpGroupSize < 1) {
        this.errors.tpGroupSize = 'TP group size must be at least 1';
      }
      
      if (!this.form.tdGroupSize) {
        this.errors.tdGroupSize = 'TD group size is required';
      } else if (this.form.tdGroupSize < 1) {
        this.errors.tdGroupSize = 'TD group size must be at least 1';
      }
      
      if (this.form.optionalTeachingUnitsCount === null || this.form.optionalTeachingUnitsCount === undefined) {
        this.errors.optionalTeachingUnitsCount = 'Optional teaching units count is required';
      } else if (this.form.optionalTeachingUnitsCount < 0) {
        this.errors.optionalTeachingUnitsCount = 'Optional teaching units count cannot be negative';
      }
      
      if (!this.form.responsibleEmail.trim()) {
        this.errors.responsibleEmail = 'Responsible email is required';
      } else if (!this.isValidEmail(this.form.responsibleEmail)) {
        this.errors.responsibleEmail = 'Please enter a valid email address';
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
        // If id exists in initialAcademicYear, include it to maintain the ID when updating
        ...(this.initialAcademicYear.id && { id: this.initialAcademicYear.id })
      };
      
      this.$emit('submit', formData);
    },
    cancel() {
      this.$emit('cancel');
    }
  },
  watch: {
    initialAcademicYear: {
      handler(newValue) {
        this.form = {
          formationName: newValue.formationName || '',
          tpGroupSize: newValue.tpGroupSize || null,
          tdGroupSize: newValue.tdGroupSize || null,
          optionalTeachingUnitsCount: newValue.optionalTeachingUnitsCount || null,
          responsibleEmail: newValue.responsibleEmail || ''
        };
      },
      deep: true
    }
  }
};
</script>

<style scoped>
.academic-year-form {
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

input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

input.is-invalid {
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
