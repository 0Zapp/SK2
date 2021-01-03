import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8762/service2/plane/';

class PlaneService {
    get() {
        return axios.get(API_URL + 'getAll', { headers: authHeader() });
    }
    add(data) {
        return axios.post(API_URL + 'addNew', data, { headers: authHeader() })
    }
    delete(id) {
        return axios.delete(API_URL + `delete/${id}`, { headers: authHeader() })
    }
}

export default new PlaneService();