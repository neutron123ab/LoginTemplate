import {createStore} from "vuex";

const store = createStore({
    state: {
        "Authorization": ''
    },
    mutations: {
        setAuthorization(state, newVal){
            state.Authorization = newVal
        }
    }
})

export default store