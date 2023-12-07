import axios, { AxiosResponse } from 'axios'; // If you're using Axios

export const baseURL = 'http://localhost:8080/';
const clientURL = baseURL + 'client/';

export type ClientType = {
    authentication: string;
    type: string;
};

export type ClientResponse = {
    clientID: string;
    authentication: string;
    type: string;
    // associatedFacility: Facility;
};

export const createClient = async (input: ClientType): Promise<ClientResponse | undefined> => {
    try {
        const response: AxiosResponse<ClientResponse> = await axios.post(clientURL + 'create', input, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        console.log(response.data);

        // Return the data as a resolved promise
        return response.data;
    } catch (error) {
        // Handle errors
        console.error('Error creating client:', error);

        // Return undefined in case of an error
        return undefined;
    }
};

export const deleteClient = async (id: string, auth: string) => {
    try {
        // Replace 'your-api-url' with your actual API endpoint
        const response = await axios.delete(`${clientURL}delete/${id}`, {
            params: {
                auth: auth,
            },
        });
        // Set the retrieved data to the state
        return response.data;
    } catch (error) {
        // Handle errors
        console.error('API Error:', error);
    }
};
