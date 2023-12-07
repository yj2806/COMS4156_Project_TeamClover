import axios, { AxiosResponse } from 'axios';
import { baseURL } from './ClientApi';
import { Facility } from './FacilityApi';

export type ListingCreate = {
    isPublic: boolean;
    groupCode: number;
    itemList: string;
    ageRequirement: number;
    veteranStatus: boolean;
    gender: string;
};

export type Listing = {
    listingID: number;
    associatedFacility: Facility;
    groupCode: number;
    itemList: string;
    ageRequirement: number;
    veteranStatus: boolean;
    gender: string;
    public: boolean;
};

const ListingURL = baseURL + 'listing/';

export const listingCreate = async (
    clientId: string,
    auth: string,
    facilityId: string,
    input: ListingCreate,
): Promise<Facility | undefined> => {
    try {
        const response: AxiosResponse<Facility> = await axios.post(
            ListingURL + 'create',
            input,

            {
                params: {
                    clientID: clientId,
                    auth,
                    facilityID: facilityId,
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

export const getAllListing = async (clientId: string, auth: string): Promise<Listing[] | undefined> => {
    try {
        const response = await axios.get<Listing[]>(ListingURL + `by_client/${clientId}`, {
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
