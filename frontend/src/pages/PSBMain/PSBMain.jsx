import 'antd/dist/antd.css';
import { Layout, Menu, Breadcrumb } from 'antd';
import { contentStyle, mainPageSiderStyle, mainPageStyle } from "../Main/Main.styles"
import React from "react"
import { FileSyncOutlined, UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons'
const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

const PSBMain = () => {
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
                    <SubMenu key="sub2" icon={<LaptopOutlined />} title="Номенклатура">
                        <Menu.Item key="5">option5</Menu.Item>
                        <Menu.Item key="6">option6</Menu.Item>
                        <Menu.Item key="7">option7</Menu.Item>
                        <Menu.Item key="8">option8</Menu.Item>
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