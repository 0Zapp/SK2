import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8762/service1/';

class AuthService {
    login(user) {
        return axios
            .post(API_URL + 'login', {
                email: user.email,
                password: user.password
            })
            .then(response => {
                if (response.headers.authorization) {
                    localStorage.setItem('user', JSON.stringify(response.headers.authorization));
                }

                return response.headers.authorization;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'user/register', {
            name: user.name,
            surname: user.surname,
            email: user.email,
            password: user.password,
            passportNumber: user.passportNumber
        });
    }

    isAdmin() {
        return axios.get(API_URL + 'user/isAdmin', { headers: authHeader() });
    }
}

export default new AuthService();