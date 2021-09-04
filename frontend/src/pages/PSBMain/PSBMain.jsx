import 'antd/dist/antd.css';
import { Layout, Menu, Breadcrumb } from 'antd';
import { contentStyle, mainPageSiderStyle, mainPageStyle } from "../Main/Main.styles"
import React, { useState } from "react"
import { FileSyncOutlined, UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons'
const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

const PSBMain = () => {
    useState()
    return (
        <Layout>
            <Sider collapsible >
                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    defaultOpenKeys={['sub1']}
                    style={{ height: '100vh', borderRight: 0 }}>
                    <SubMenu key="sub1" icon={<UserOutlined />} title="Загрузить">
                        <Menu.Item key="1">Документ</Menu.Item>
                        <Menu.Item key="2">Архив</Menu.Item>
                    </SubMenu>
                    <SubMenu key="sub2" icon={<LaptopOutlined />} title="Периоды">
                        <Menu.Item key="5">1 полугодие 2021</Menu.Item>
                        <Menu.Item key="6">2 полугодие 2020</Menu.Item>
                    </SubMenu>
                    <SubMenu key="sub3" icon={<LaptopOutlined />} title="Отчеты">
                        <Menu.Item key="7">1 полугодие 2021</Menu.Item>
                        <Menu.Item key="8">2 полугодие 2020</Menu.Item>
                    </SubMenu>
                </Menu>
            </Sider>
            <Content style={{padding: '20px'}}>
                Main asdfjalksdjfklasjdkfljakljsdflkjadskljfasdfasdjfaklsdjfklajsdklfjaklsdjfkljadskljfkajsdklfjaksdljfkalsjdfklajsdklfjasdklfjasdkljf
            </Content>
        </Layout>
    )
}

export default PSBMain