import React, { useState } from 'react';
import {
    Stack,
    TextField,
    Button,
    Checkbox,
    FormGroup,
    FormControlLabel,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
} from '@mui/material';
import PageLayout from '../PageLayout';

const ListingEdit: React.FC = () => {
    const [isPublic, setIsPublic] = useState(false);
    const [isVeteran, setIsVeteran] = useState(false);

    const [gender, setGender] = useState<string>('Female');
    const handleChange = (event: SelectChangeEvent) => {
        setGender(event.target.value as string);
    };

    console.log(isPublic, isVeteran);
    return (
        <PageLayout>
            <Stack gap={2} width={500} p={2}>
                <TextField label="Group Code" type="number" />
                <TextField label="Item List" />
                <FormControl fullWidth>
                    <InputLabel id="gender">Gender</InputLabel>
                    <Select labelId="gender" value={gender} label="Gender" onChange={handleChange}>
                        <MenuItem value={'Female'}>Female</MenuItem>
                        <MenuItem value={'Male'}>Male</MenuItem>
                        <MenuItem value={'Other'}>Other</MenuItem>
                    </Select>
                </FormControl>
                <FormGroup>
                    <FormControlLabel
                        control={<Checkbox value={isPublic} onChange={() => setIsPublic(!isPublic)} />}
                        label="Public"
                    />
                    <FormControlLabel
                        control={<Checkbox value={isVeteran} onChange={() => setIsVeteran(!isVeteran)} />}
                        label="Veteran"
                    />
                </FormGroup>
                <Button variant="contained">Edit Listing</Button>
            </Stack>
        </PageLayout>
    );
};

export default ListingEdit;
