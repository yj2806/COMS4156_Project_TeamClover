import { Stack, TextField, Button } from '@mui/material';
import React, { useState } from 'react';
import PageLayout from '../PageLayout';
import { deleteClient } from '../../api/ClientApi';

const ClientDelete: React.FC = () => {
    const [id, setId] = useState<string>('');
    const [authentication, setAuthentication] = useState<string>('');

    return (
        <PageLayout>
            <Stack gap={2} width={500}>
                <TextField
                    label="Client ID"
                    type="string"
                    value={id}
                    inputProps={{ inputMode: 'text' }}
                    onChange={(event) => {
                        setId((event.target as HTMLTextAreaElement).value);
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
                <Button
                    disabled={!authentication}
                    onClick={async () => {
                        await deleteClient(id, authentication);
                    }}
                    variant="contained"
                >
                    Delete Client
                </Button>
            </Stack>
        </PageLayout>
    );
};

export default ClientDelete;
