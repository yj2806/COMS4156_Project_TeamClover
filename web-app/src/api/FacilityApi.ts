import axios, { AxiosResponse } from 'axios';
import { baseURL } from './ClientApi';

export type Facility = {
    facilityID: string;
    latitude: number;
    longitude: number;
    email: string;
    phone: string;
    hours: string;
    public: boolean;
};

export type FacilityCreateDTO = {
    latitude: number;
    longitude: number;
    isPublic: boolean;
    email: string;
    phone: string;
    hours: string;
};

const facilityURL = baseURL + 'facility/';

export const facilityCreate = async (
    clientId: string,
    auth: string,
    input: FacilityCreateDTO,
): Promise<Facility | undefined> => {
    try {
        const response: AxiosResponse<Facility> = await axios.post(
            facilityURL + 'create',
            input,

            {
                params: {
                    clientID: clientId,
                    auth,
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            },
        );

        console.log('Response:', response.data);
        // Handle the response as needed
        return response.data;
    } catch (error) {
        console.error('Error making POST request:', error);
        // Handle errors
        return undefined;
    }
};

export const facilityUpdate = async (
    facilityId: string,
    clientId: string,
    auth: string,
    input: FacilityCreateDTO,
): Promise<Facility | undefined> => {
    try {
        const response: AxiosResponse<Facility> = await axios.post(
            facilityURL + 'update/' + facilityId,
            input,

            {
                params: {
                    clientID: clientId,
                    auth,
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            },
        );

        console.log('Response:', response.data);
        // Handle the response as needed
        return response.data;
    } catch (error) {
        console.error('Error making POST request:', error);
        // Handle errors
        return undefined;
    }
};

export const getFacility = async (facilityId: string): Promise<Facility | undefined> => {
    try {
        const response = await axios.get<Facility>(facilityURL + `${facilityId}`, {
            params: {
                id: facilityId,
            },
            headers: {
                'Content-Type': 'application/json',
            },
        });

        console.log('Response:', response.data);
        return response.data;
        // Handle the response as needed
    } catch (error) {
        console.error('Error making GET request:', error);
        // Handle errors
        return undefined;
    }
};

export const getFacilityByClient = async (clientId: string, auth: string): Promise<Facility[] | undefined> => {
    try {
        const response = await axios.get<Facility[]>(facilityURL + `by_client/${clientId}`, {
            params: {
                auth: auth,
            },
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.data;
        // Handle the response as needed
    } catch (error) {
        console.error('Error making GET request:', error);
        // Handle errors
        return undefined;
    }
};

export const deleteFacility = async (clientId: string, auth: string, facilityId: string) => {
    try {
        // Replace 'your-api-url' with your actual API endpoint
        const response = await axios.delete(`${facilityURL}delete/${facilityId}`, {
            params: {
                clientID: clientId,
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
