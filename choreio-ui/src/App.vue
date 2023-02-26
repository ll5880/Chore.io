<template>
  <div>
    <img alt="chore-io" src="./assets/chore-io.png">
    <HelloWorld msg="Welcome to Chore.io"/>
    <Chorescomponent msg="Heres a list of chores"/>
  </div>
</template>

<script>
import Chorescomponent from './components/Chorescomponent.vue';
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HelloWorld,
    Chorescomponent,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
