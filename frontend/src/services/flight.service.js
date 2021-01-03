import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8762/service2/flight/';

class FlightService {
    show(page, size) {
        return axios.get(API_URL + `show/${page}/${size}`, { headers: authHeader() });
    }
    search(page, size, data) {
        return axios.post(API_URL + `search/${page}/${size}`, data, { headers: authHeader() })
    }
    add(data) {
        return axios.post(API_URL + 'addNew', data, { headers: authHeader() })
    }
    delete(id) {
        return axios.delete(API_URL + `delete/${id}`, { headers: authHeader() })
    }
}

export default new FlightService();