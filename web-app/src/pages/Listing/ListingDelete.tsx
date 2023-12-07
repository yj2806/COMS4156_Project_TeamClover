import React, { useState } from 'react';
import PageLayout from '../PageLayout';
import { Stack, TextField, Button } from '@mui/material';
import { deleteListing } from '../../api/ListingApi';

const ListingDelete: React.FC = () => {
    const [authentication, setAuthentication] = useState<string>('');
    const [clientId, setClientId] = useState<string | undefined>();
    const [listingId, setListingId] = useState<string | undefined>();

    return (
        <PageLayout>
            <Stack gap={3}>
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
                    disabled={!(clientId && listingId && authentication)}
                    onClick={async () => {
                        clientId && listingId && (await deleteListing(clientId, authentication, listingId));
                    }}
                >
                    Delete Listing
                </Button>
            </Stack>
        </PageLayout>
    );
};

export default ListingDelete;
