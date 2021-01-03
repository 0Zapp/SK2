import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8762/service3/ticket/';

class TicketService {

    addTicket(data) {
        return axios.post(API_URL + 'addNew', data, { headers: authHeader() })
    }

    getTickets() {
        return axios.get(API_URL + 'getAll', { headers: authHeader() })
    }
}

export default new TicketService();