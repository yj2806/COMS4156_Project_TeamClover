import {
    AppBar,
    Box,
    CssBaseline,
    Divider,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Typography,
} from '@mui/material';
import React from 'react';

type Props = {
    children?: React.ReactNode;
};

type MenuListing = {
    name: string;
    href: string;
};
const PageLayout: React.FC<Props> = ({ children }) => {
    const drawerWidth = 240;
    const clientMenuItems: MenuListing[] = [
        { name: 'Client Create', href: '/client/create' },
        { name: 'Client Delete', href: '/client/delete' },
    ];
    const listingMenuItems: MenuListing[] = [
        { name: 'Listing Create', href: '/listing/create' },
        { name: 'View Listing', href: '/listing/view' },
        { name: 'Edit Listing', href: '/listing/edit' },
        { name: 'Search Listing', href: '/listing/search' },
    ];

    const facilityMenuItems: MenuListing[] = [
        { name: 'Facility Create', href: '/facility/create' },
        { name: 'Edit Facility', href: '/facility/edit' },
        { name: 'View Facility', href: '/facility/view' },
        { name: 'Delete Facility', href: '/facility/delete' },
    ];

    return (
        <CssBaseline>
            <Box sx={{ display: 'flex' }}>
                <AppBar position="fixed" sx={{ height: '70px', width: `calc(100% - ${drawerWidth}px)` }}>
                    <Typography variant="h5" sx={{ p: 2 }}>
                        Team Clove - Front End
                    </Typography>
                </AppBar>
                <Drawer
                    sx={{
                        width: drawerWidth,
                        flexShrink: 0,
                        '& .MuiDrawer-paper': {
                            width: drawerWidth,
                            boxSizing: 'border-box',
                        },
                    }}
                    variant="permanent"
                    anchor="left"
                >
                    <List>
                        <ListItem sx={{ fontWeight: 'bold' }}>Client</ListItem>
                        {clientMenuItems.map((item) => (
                            <ListItem key={item.name} disablePadding>
                                <ListItemButton href={item.href}>
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary={item.name} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Divider />
                    <List>
                        <ListItem sx={{ fontWeight: 'bold' }}>Facility</ListItem>
                        {facilityMenuItems.map((item) => (
                            <ListItem key={item.name} disablePadding>
                                <ListItemButton href={item.href}>
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary={item.name} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Divider />
                    <List>
                        <ListItem sx={{ fontWeight: 'bold' }}>Listing</ListItem>
                        {listingMenuItems.map((item) => (
                            <ListItem key={item.name} disablePadding>
                                <ListItemButton href={item.href}>
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary={item.name} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                </Drawer>
                <Box sx={{ mt: '70px', pt: 2 }}>{children}</Box>
            </Box>
        </CssBaseline>
    );
};

export default PageLayout;
