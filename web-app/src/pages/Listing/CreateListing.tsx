import React, { useState } from 'react';
import {
    Stack,
    TextField,
    Button,
    Checkbox,
    FormControlLabel,
    MenuItem,
    Select,
    Divider,
    FormGroup,
    SelectChangeEvent,
    Typography,
    FormControl,
    InputLabel,
} from '@mui/material';
import PageLayout from '../PageLayout';
import { ListingCreate, listingCreate } from '../../api/ListingApi';

const CreateListing: React.FC = () => {
    const [formValues, setFormValues] = useState<ListingCreate>({
        isPublic: true,
        groupCode: 0,
        itemList: '',
        ageRequirement: 0,
        veteranStatus: true,
        gender: '',
    });

    const [listingId, setListingId] = useState<string | undefined>();
    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | { name: string; value: unknown }>,
    ) => {
        const { name, value } = e.target;

        setFormValues((prevValues) => ({
            ...prevValues,
            [name]: (e.target as HTMLInputElement).type === 'checkbox' ? (e.target as HTMLInputElement).checked : value,
        }));
    };

    const handleSelectChange = (e: SelectChangeEvent) => {
        const { name, value } = e.target;
        setFormValues((prevValues) => ({
            ...prevValues,
            [name as string]: value,
        }));
    };

    const handleSubmit = async () => {
        setListingId(undefined);
        const response =
            clientId && facilityId && (await listingCreate(clientId, authentication, facilityId, formValues));
        response && setListingId(response.listingID);
    };

    const [authentication, setAuthentication] = useState<string>('');
    const [clientId, setClientId] = useState<string | undefined>();
    const [facilityId, setFacilityId] = useState<string | undefined>();

    return (
        <PageLayout title="Create Listing">
            <Stack gap={2} width={500}>
                <TextField
                    label="Client ID"
                    type="string"
                    value={clientId}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setClientId((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <TextField
                    label="Client Authentication"
                    type="string"
                    value={authentication}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setAuthentication((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <TextField
                    label="Facility ID"
                    type="string"
                    value={facilityId}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setFacilityId((event.target as HTMLTextAreaElement).value);
                    }}
                />

                <Divider />

                <FormGroup>
                    <Stack gap={2}>
                        <TextField
                            label="Item List"
                            name="itemList"
                            value={formValues.itemList}
                            onChange={handleInputChange}
                        />
                        <FormControl fullWidth>
                            <InputLabel id="gender-type">Gender</InputLabel>
                            <Select
                                label="Gender"
                                name="gender"
                                value={formValues.gender}
                                onChange={handleSelectChange}
                            >
                                <MenuItem value="male">Male</MenuItem>
                                <MenuItem value="female">Female</MenuItem>
                                <MenuItem value="other">Other</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField
                            label="Group Code"
                            name="groupCode"
                            type="number"
                            value={formValues.groupCode}
                            onChange={handleInputChange}
                        />
                        <TextField
                            label="Age Requirement"
                            name="ageRequirement"
                            type="number"
                            value={formValues.ageRequirement}
                            onChange={handleInputChange}
                        />
                        <FormControlLabel
                            control={
                                <Checkbox
                                    name="veteranStatus"
                                    checked={formValues.veteranStatus}
                                    onChange={handleInputChange}
                                />
                            }
                            label="Veteran Status"
                        />
                        <FormControlLabel
                            control={
                                <Checkbox name="isPublic" checked={formValues.isPublic} onChange={handleInputChange} />
                            }
                            label="Is Public"
                        />
                    </Stack>
                </FormGroup>
                <Button
                    variant="contained"
                    onClick={() => {
                        handleSubmit();
                    }}
                >
                    Create Listing
                </Button>
                {listingId && <Typography>New Listing ID: {listingId}</Typography>}
            </Stack>
        </PageLayout>
    );
};

export default CreateListing;
