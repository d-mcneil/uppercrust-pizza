<template>
  <footer>
    <div id="footer-spacer"></div>
    <div id="footer-text">
      <span>UpperCrust Pizza </span>&nbsp;- Elevating your slice since 2023.
    </div>
    <div id="employees-portal-link">
      <router-link
        v-if="!$route.path.includes('employees') && !isCustomerLoggedIn"
        :to="{ name: 'employee-login' }"
        >Employees</router-link
      >
    </div>
  </footer>
</template>

<script>
export default {
  computed: {
    isCustomerLoggedIn() {
      const { token, user } = this.$store.state;
      if (!token) {
        return false;
      }

      const roles = user.authorities.map((role) =>
        role.name.toLowerCase().replace("role_", "")
      );

      if (
        roles.indexOf("employee") > -1 ||
        roles.indexOf("admin") > -1 ||
        roles.indexOf("demo_employee") > -1
      ) {
        return false;
      } else {
        return true;
      }
    },
  },
};
</script>
<style scoped>
footer {
  display: none;
}

@media only screen and (min-width: 768px) {
  footer {
    display: block;
    border-top-left-radius: var(--section-border-radius);
    border-top-right-radius: var(--section-border-radius);
    background-color: var(--white-color);
    margin-top: auto;
    display: flex;
    align-items: center;
    font-size: 0.75rem;
    padding: 0 15px;
    width: var(--header-footer-width);
    font-weight: bold;
  }

  footer div#footer-text {
    text-align: center;
    padding: 10px;
  }

  footer div#footer-text span {
    color: var(--primary-color);
  }

  footer div#footer-spacer,
  footer div#employees-portal-link {
    flex-grow: 1;
    flex-shrink: 1;
    flex-basis: 4.5rem;
  }

  footer div#employees-portal-link {
    text-align: right;
  }

  footer div#employees-portal-link a {
    color: var(--secondary-color);
    text-decoration: none;
  }
}
</style>
