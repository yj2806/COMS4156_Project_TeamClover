import axios, { AxiosResponse } from 'axios';
import { baseURL } from './ClientApi';
import { Facility } from './FacilityApi';
import { ListingGroupSearch } from '../pages/Listing/Search/ListingSearchGroupCode';
import { ListingSearchByFilter } from '../pages/Listing/Search/ListingSearchWithFilter';

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

export const getListingById = async (listingId: string): Promise<Listing | undefined> => {
    try {
        const response = await axios.get<Listing>(ListingURL + `${listingId}`, {
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

export const listingUpdate = async (
    listingId: string,
    clientId: string,
    auth: string,
    input: ListingCreate,
): Promise<Facility | undefined> => {
    try {
        const response: AxiosResponse<Facility> = await axios.put(
            ListingURL + 'update/' + listingId,
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

export const searchByGroupCode = async (userInput: ListingGroupSearch): Promise<Listing[] | undefined> => {
    try {
        const response = await axios.get<Listing[]>(ListingURL + 'search_with_group_code', {
            params: userInput,
        });

        // Handle the API response data as needed
        return response.data;
    } catch (error) {
        // Handle errors
        console.error('Error fetching data:', error);
        return undefined;
    }
};

export const searchByFilter = async (userInput: ListingSearchByFilter): Promise<Listing[] | undefined> => {
    try {
        const response = await axios.get<Listing[]>(ListingURL + 'search_with_filter', {
            params: userInput,
        });

        // Handle the API response data as needed
        return response.data;
    } catch (error) {
        // Handle errors
        console.error('Error fetching data:', error);
        return undefined;
    }
};
