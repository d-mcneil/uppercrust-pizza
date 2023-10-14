<template>
  <section id="employee-login" class="employee-section">
    <horizontal-hero></horizontal-hero>
    <form @submit.prevent="login">
      <h1>Please Sign In</h1>
      <an-alert
        v-if="invalidCredentials"
        message="Invalid username and password!"
      ></an-alert>
      <an-alert
        v-if="this.$route.query.registration"
        message="Thank you for registering, please sign in."
      />
      <user-input
        label="Username: "
        inputId="employee-login-username-input"
        inputType="text"
        :default-value="user.username"
        v-model="user.username"
        :isAutofocus="true"
        :isRequired="true"
      ></user-input>

      <user-input
        label="Password: "
        :default-value="user.password"
        inputId="employee-login-password-input"
        inputType="password"
        v-model="user.password"
        :isRequired="true"
      ></user-input>

      <small-button buttonText="Sign in" buttonType="submit"></small-button>

      <div id="sign-in-as-demo-employee-wrapper">
        <small-button
          buttonText="Sign In as Demo Employee"
          id="sign-in-as-demo-employee"
          :click-handler="
            () => {
              setCredentialsToDemoEmployee();
              login();
            }
          "
        />
      </div>
    </form>
  </section>
</template>

<script>
import AnAlert from "../components/AnAlert.vue";
import HorizontalHero from "../components/HorizontalHero.vue";
import SmallButton from "../components/SmallButton.vue";
import UserInput from "../components/UserInput";
import authService from "../services/AuthService";

export default {
  name: "login",
  components: { UserInput, SmallButton, HorizontalHero, AnAlert },
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    setCredentialsToDemoEmployee() {
      this.user.username = "demo_employee";
      this.user.password = "password";
    },
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            let user = {
              ...response.data.user,
              userData: response.data.userData,
            };
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", user);

            const roles = response.data.user.authorities.map((role) =>
              role.name.toLowerCase().replace("role_", "")
            );
            const isAdmin = (roles) => roles.indexOf("admin") > -1;
            const isEmployee = (roles) =>
              roles.indexOf("employee") > -1 ||
              roles.indexOf("demo_employee") > -1;

            if (isAdmin(roles) || isEmployee(roles)) {
              this.$router.push({ name: "pending-orders" });
            } else {
              this.$router.push({ name: "home" });
            }
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
  beforeCreate() {
    if (Object.keys(this.$store.state.cart).length) {
      this.$store.commit("CLEAR_CART");
    }
    if (this.$store.state.token) {
      this.$router.push({ name: "pending-orders" });
    }
  },
};
</script>

<style scoped>
button#sign-in-as-demo-employee {
  background-color: var(--primary-color);
  border-color: var(--primary-color);
  margin-top: 0;
}

div#sign-in-as-demo-employee-wrapper {
  margin-top: 1.5rem;
  padding-top: 1.5rem;
  border-top: 2px solid var(--dark-color);
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
