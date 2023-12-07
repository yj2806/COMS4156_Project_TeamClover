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

const ListingURL = baseURL + 'client/';
