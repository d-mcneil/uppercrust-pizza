<template>
  <div class="form-input-group" @click="(event) => event.stopPropagation()">
    <div class="form-input-label-wrapper">
      <label :for="inputId">{{ label }}</label>
    </div>
    <input
      :type="inputType"
      :id="inputId"
      v-model="localValue"
      :autofocus="isAutofocus"
      :required="isRequired"
    />
  </div>
</template>

<script>
export default {
  props: [
    "label",
    "inputId",
    "inputType",
    "isAutofocus",
    "isRequired",
    "defaultValue",
  ],
  data() {
    return {
      localValue: "",
    };
  },
  created() {
    if (this.defaultValue) {
      this.localValue = this.defaultValue;
    }
  },
  watch: {
    localValue(newValue) {
      // Emit an input event to the parent to update the parent component's value
      this.$emit("input", newValue);
    },
    defaultValue(newValue) {
      this.localValue = newValue;
    },
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 24rem;
}

.form-input-group input,
.form-input-group .form-input-label-wrapper {
  width: 100%;
  max-width: 24rem;
}

.form-input-group input {
  display: block;
  margin-top: 2px;
  padding: 5px;
}
</style>
