import { List, Card, Layout, Row, Col } from 'antd'
import { searchStyle } from '../Main/Main.styles'
import { useState, useMemo, useEffect } from 'react'
import moment from 'moment'
import { Input, Form, DatePicker, Tooltip } from 'antd'
import { useHttp } from '../../hooks/http.hook'
import DocumentGrid from './DocumentGrid'
import { SearchOutlined } from '@ant-design/icons'

const { Header } = Layout

const DocumentsListMain = () => {
    const [searchQuery, setSearchQuery] = useState('')
    const [documents, setDocuments] = useState([])

    const [startDate, setStartDate] = useState(null)
    const [endDate, setEndDate] = useState(moment().endOf('day'))

    const { loading, request } = useHttp()
    const filteredDocuments = useMemo(() => {
        const start = moment(startDate, 'DD-MM-YYYY').valueOf()
        const end = moment(endDate, 'DD-MM-YYYY').valueOf()

        if (start && end) {
            const _documents = documents.filter(document => {
                const dateOfCreation = moment(
                    document.date_of_creation,
                    'DD.MM.YYYY'
                ).valueOf()

                return dateOfCreation > start && dateOfCreation < end
            })

            return _documents.sort(document => {
                return document.id
            })
        }

        return documents
    }, [documents, startDate, endDate])

    const filteredAndSearchedDocuments = useMemo(() => {
        return filteredDocuments.filter(document =>
            document.title.toLowerCase().includes(searchQuery.toLowerCase())
        )
    }, [searchQuery, filteredDocuments])

    useEffect(() => {

        const getDocuments = async () => {
            const data = await request('/api/index', 'GET', null)
            console.log("Data")
            console.log(data)
            setDocuments(data)
        }

        getDocuments()
    }, [request])


    return (
        <Layout>
            <Form css={searchStyle}>
                <Row>
                    <Col span={8}>
                        <Form.Item>
                            <Tooltip placement="bottom" title="Найти документ">
                                <Input
                                    value={searchQuery}
                                    onChange={e => {
                                        localStorage.setItem('searchQuery', e.target.value)
                                        setSearchQuery(e.target.value)
                                    }}
                                    prefix={<SearchOutlined />}
                                    allowClear
                                />
                            </Tooltip>
                        </Form.Item>
                    </Col>
                    <Col span={8} offset={4}>
                        <Form.Item>
                            <Tooltip placement="right" title="Фильтр по дате">
                                <DatePicker.RangePicker
                                    disabledDate={current =>
                                        current && current > moment().endOf('day')
                                    }
                                    defaultValue={[null]}
                                    value={[startDate, endDate]}
                                    onChange={dates => {
                                        setStartDate(dates[0])
                                        setEndDate(dates[1])
                                    }}
                                    bordered={true}
                                    allowClear={false}
                                />
                            </Tooltip>
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
            <DocumentGrid isLoading={loading} documents={filteredAndSearchedDocuments} />
        </Layout>
    )
}

export default DocumentsListMain
