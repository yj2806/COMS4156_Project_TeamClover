import React, { useState } from 'react';
import {
    Stack,
    TextField,
    Button,
    Checkbox,
    FormGroup,
    FormControlLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    Divider,
} from '@mui/material';
import PageLayout from '../PageLayout';
import { ListingCreate, getListingById, listingUpdate } from '../../api/ListingApi';

const ListingEdit: React.FC = () => {
    const [formValues, setFormValues] = useState<ListingCreate>({
        isPublic: true,
        groupCode: 0,
        itemList: '',
        ageRequirement: 0,
        veteranStatus: true,
        gender: '',
    });

    console.log(formValues);

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
        clientId && listingId && (await listingUpdate(listingId, clientId, authentication, formValues));
    };

    const [authentication, setAuthentication] = useState<string>('');
    const [clientId, setClientId] = useState<string | undefined>();
    const [listingId, setListingId] = useState<string | undefined>();

    return (
        <PageLayout>
            <Stack gap={2}>
                <TextField
                    label="Listing ID"
                    type="string"
                    value={listingId}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setListingId((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <Button
                    variant="contained"
                    onClick={async () => {
                        const response = listingId && (await getListingById(listingId));
                        response &&
                            setFormValues({
                                isPublic: response.public,
                                groupCode: response.groupCode,
                                itemList: response.itemList,
                                ageRequirement: response.ageRequirement,
                                veteranStatus: response.veteranStatus,
                                gender: response.gender,
                            });
                    }}
                >
                    Get Listing
                </Button>
            </Stack>
            <Divider />
            <Stack gap={2} width={500} pt={3}>
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

                <Divider />

                <FormGroup>
                    <Stack gap={2}>
                        <TextField
                            label="Item List"
                            name="itemList"
                            value={formValues.itemList}
                            onChange={handleInputChange}
                        />
                        <Select label="Gender" name="gender" value={formValues.gender} onChange={handleSelectChange}>
                            <MenuItem value="male">Male</MenuItem>
                            <MenuItem value="female">Female</MenuItem>
                            <MenuItem value="other">Other</MenuItem>
                        </Select>
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
                    Update Listing
                </Button>
            </Stack>
        </PageLayout>
    );
};

export default ListingEdit;
