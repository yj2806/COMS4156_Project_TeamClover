import React, { useState } from 'react';
import { Button, FormControlLabel, FormGroup, Stack, Switch, TextField, Typography } from '@mui/material';
import PageLayout from '../PageLayout';
import { FacilityCreateDTO, facilityCreate } from '../../api/FacilityApi';

const CreateFacility: React.FC = () => {
    const [authentication, setAuthentication] = useState<string>('');
    const [clientId, setClientId] = useState<string>('');

    const [facilityId, setFacilityId] = useState<string | undefined>();

    const [formValues, setFormValues] = useState<FacilityCreateDTO>({
        latitude: 0,
        longitude: 0,
        isPublic: true,
        email: '',
        phone: '',
        hours: '',
    });

    console.log(facilityId);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormValues((prevValues) => ({
            ...prevValues,
            [name]: name === 'isPublic' ? (e.target as HTMLInputElement).checked : value,
        }));
    };

    console.log(formValues);

    return (
        <PageLayout title="Create Facility">
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
                    label="Authentication"
                    type="string"
                    value={authentication}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setAuthentication((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <FormGroup>
                    <Stack gap={2}>
                        <TextField
                            label="Latitude"
                            type="number"
                            name="latitude"
                            value={formValues.latitude}
                            onChange={handleInputChange}
                        />
                        <TextField
                            label="Longitude"
                            type="number"
                            name="longitude"
                            value={formValues.longitude}
                            onChange={handleInputChange}
                        />
                        <FormControlLabel
                            control={
                                <Switch checked={formValues.isPublic} onChange={handleInputChange} name="isPublic" />
                            }
                            label="Is Public"
                        />
                        <TextField
                            label="Email"
                            type="email"
                            name="email"
                            value={formValues.email}
                            onChange={handleInputChange}
                        />
                        <TextField
                            label="Phone"
                            type="tel"
                            name="phone"
                            value={formValues.phone}
                            onChange={handleInputChange}
                        />
                        <TextField label="Hours" name="hours" value={formValues.hours} onChange={handleInputChange} />
                    </Stack>
                </FormGroup>
                <Button
                    variant="contained"
                    onClick={async () => {
                        setFacilityId(undefined);
                        const response = await facilityCreate(clientId, authentication, formValues);
                        response && setFacilityId(response.facilityID);
                    }}
                >
                    Create Facility
                </Button>

                {facilityId && <Typography>New Facility ID: {facilityId}</Typography>}
            </Stack>
        </PageLayout>
    );
};

export default CreateFacility;
