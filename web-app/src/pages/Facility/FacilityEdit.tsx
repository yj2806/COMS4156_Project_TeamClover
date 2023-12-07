import { Button, Divider, FormControlLabel, FormGroup, Stack, Switch, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import PageLayout from '../PageLayout';
import { FacilityCreateDTO, facilityUpdate, getFacility } from '../../api/FacilityApi';

const FacilityEdit: React.FC = () => {
    const [message, setMessage] = useState<string | undefined>();

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

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormValues((prevValues) => ({
            ...prevValues,
            [name]: name === 'isPublic' ? (e.target as HTMLInputElement).checked : value,
        }));
    };

    return (
        <PageLayout title="Facility Edit">
            <Stack gap={2}>
                <TextField
                    label="Facility ID"
                    type="string"
                    value={facilityId}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setFacilityId((event.target as HTMLTextAreaElement).value);
                    }}
                />
                <Button
                    variant="contained"
                    onClick={async () => {
                        const response = facilityId && (await getFacility(facilityId));
                        response &&
                            setFormValues({
                                latitude: response.latitude,
                                longitude: response.longitude,
                                isPublic: response.public,
                                email: response.email,
                                phone: response.phone,
                                hours: response.hours,
                            });
                        // console.log(response);
                    }}
                >
                    Get Facility
                </Button>
            </Stack>
            <Divider />
            <Stack gap={2} width={500} pt={2}>
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
                        setMessage(undefined);
                        setFacilityId(undefined);
                        const response = await facilityUpdate(facilityId || '', clientId, authentication, formValues);
                        response && setFacilityId(response.facilityID);
                        if (response) {
                            setMessage('Successful');
                        } else {
                            setMessage('Unble to update, please check fields');
                        }
                    }}
                >
                    Update Facility
                </Button>
                {message && <Typography>{message}</Typography>}
            </Stack>
        </PageLayout>
    );
};

export default FacilityEdit;
