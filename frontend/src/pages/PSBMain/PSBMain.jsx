import { Layout, Menu } from 'antd'
import React from 'react'
import { CloudUploadOutlined, LaptopOutlined, FileSearchOutlined } from '@ant-design/icons'
import { Route, Switch, Link, useRouteMatch, Redirect } from 'react-router-dom'
import { insideAppRoutes } from '../../Routes/routes.config'
import {
    PSB_ARCHIVE_UPLOAD,
    PSB_DOCUMENTS_PAGE,
    PSB_FILE_UPLOAD,
    PSB_DOCUMENTS_LIST,
} from '../../Routes/routes.paths'

const { Content, Sider } = Layout
const { SubMenu } = Menu

const PSBMain = () => {
    const match = useRouteMatch()
    console.log(match.path)
    console.log(match.url)

    return (
        <Layout>
            <Sider collapsible>
                <Menu
                    theme="dark"
                    mode="inline"
                    style={{ height: '100vh', borderRight: 0 }}
                >
                    <Menu.Item key="0" icon={<FileSearchOutlined />}>
                        <Link to={`${match.url}/${PSB_DOCUMENTS_LIST}`}>Документы</Link> 
                    </Menu.Item>
                    <SubMenu key="sub1" icon={<CloudUploadOutlined />} title="Загрузить">
                        <Menu.Item key="1">
                            <Link to={`${match.url}/${PSB_FILE_UPLOAD}`}>Документ</Link>
                        </Menu.Item>
                        <Menu.Item key="2">
                            <Link to={`${match.url}/${PSB_ARCHIVE_UPLOAD}`}>Архив</Link>
                        </Menu.Item>
                    </SubMenu>
                    {/* <SubMenu key="sub2" icon={<LaptopOutlined />} title="Периоды">
                        <Menu.Item key="5">1 полугодие 2021</Menu.Item>
                        <Menu.Item key="6">2 полугодие 2020</Menu.Item>
                    </SubMenu>
                    <SubMenu key="sub3" icon={<LaptopOutlined />} title="Отчеты">
                        <Menu.Item key="7">1 полугодие 2021</Menu.Item>
                        <Menu.Item key="8">2 полугодие 2020</Menu.Item>
                    </SubMenu> */}
                </Menu>
            </Sider>
            <Content style={{ padding: '50px' }}>
                <Switch>
                    {insideAppRoutes.map(route => (
                        <Route
                            key={route}
                            exact={route.exact}
                            path={`${match.path}/${route.path}`}
                        >
                            {route.component}
                        </Route>
                    ))}
                    <Redirect to={PSB_DOCUMENTS_PAGE} />
                </Switch>
            </Content>
        </Layout>
    )
}

export default PSBMain
