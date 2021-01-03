import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8762/service1/card/';

class CardService {

    addCard(data) {
        return axios.post(API_URL + 'addNew', data, { headers: authHeader() })
    }

    getCards() {
        return axios.get(API_URL + 'getAll', { headers: authHeader() })
    }
}

export default new CardService();