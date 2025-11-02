import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || ''
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },
    REMOVE_TOKEN(state) {
      state.token = ''
      localStorage.removeItem('token')
    }
  },
  actions: {
    setToken({ commit }, token) {
      commit('SET_TOKEN', token)
    },
    removeToken({ commit }) {
      commit('REMOVE_TOKEN')
    }
  },
  modules: {}
})

