import {
    AppBar,
    Box,
    CssBaseline,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Stack,
    Typography,
} from '@mui/material';
import React from 'react';

const PageLayout: React.FC = () => {
    return (
        <CssBaseline>
            <Stack sx={{ display: 'flex' }}>
                <AppBar position="sticky">
                    <Typography variant="h5" sx={{ p: 2 }}>
                        Team Clove - Front End
                    </Typography>
                </AppBar>
                <Drawer sx={{ width: 500 }} variant="permanent" anchor="left">
                    <List>
                        {['Inbox', 'Starred', 'Send email', 'Drafts'].map((text, index) => (
                            <ListItem key={text} disablePadding>
                                <ListItemButton>
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary={text} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                </Drawer>
            </Stack>
        </CssBaseline>
    );
};

export default PageLayout;
