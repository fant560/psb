import { Layout, Row, Col, Radio } from 'antd'
import { searchStyle } from '../Main/Main.styles'
import { useState, useMemo, useEffect } from 'react'
import moment from 'moment'
import { Input, Form, DatePicker, Tooltip } from 'antd'
import { useHttp } from '../../hooks/http.hook'
import DocumentGrid from '../../components/DocumentGrid/DocumentGrid'
import { SearchOutlined } from '@ant-design/icons'

const DocumentsList = () => {
  const [searchQuery, setSearchQuery] = useState('')
  const [documents, setDocuments] = useState(() => [])

  const [startDate, setStartDate] = useState(null)
  const [endDate, setEndDate] = useState(null)
  const [selectedRadio, setSelectedRadio] = useState('all')

  const { loading, request } = useHttp()

  useEffect(() => {
    const getDocuments = async () => {
      const data = await request('/api/index', 'GET', null)
      console.log(data)
      setDocuments(data)
    }

    getDocuments()
    // eslint-disable-next-line
  }, [])

  const filteredOfDateDocuments = useMemo(() => {
    const start = moment(startDate, 'DD-MM-YYYY').valueOf()
    const end = moment(endDate, 'DD-MM-YYYY').valueOf()

    if (start && end) {
      const _documents = documents.filter(document => {
        const date = moment(document.dateOfUploading)
        const dateComponent = date.utc().format('YYYY-MM-DD')
        const dateOfUploading = moment(dateComponent, 'YYYY-MM-DD').valueOf()

        return dateOfUploading > start && dateOfUploading < end
      })

      return _documents.sort(document => {
        return document.id
      })
    }

    return documents
  }, [documents, startDate, endDate])

  const filteredOfSendedInSystemDocuments = useMemo(() => {
    if (selectedRadio === 'all') {
      return filteredOfDateDocuments
    } else if (selectedRadio === 'sent') {
      return filteredOfDateDocuments.filter(document => document.unrecognized)
    } else if (selectedRadio === 'notSent') {
      return filteredOfDateDocuments.filter(document => !document.unrecognized)
    }
  }, [selectedRadio, filteredOfDateDocuments])

  const filteredAndSearchedDocuments = useMemo(() => {
    return filteredOfSendedInSystemDocuments.filter(document =>
      document.title
        ? document.title.toLowerCase().includes(searchQuery.toLowerCase())
        : true
    )
  }, [searchQuery, filteredOfSendedInSystemDocuments])

  return (
    <Layout>
      <Form css={searchStyle}>
        <Row>
          <Col span={7}>
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
          <Col span={7} offset={1}>
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
          <Col span={7} offset={1}>
            <Form.Item>
              <Tooltip placement="right" title="Фильтр по валидности">
                <Radio.Group
                  value={selectedRadio}
                  onChange={e => setSelectedRadio(e.target.value)}
                  buttonStyle="solid"
                >
                  <Radio.Button value="all">Все</Radio.Button>
                  <Radio.Button value="sent">Отправленные</Radio.Button>
                  <Radio.Button value="notSent">Не отправленные</Radio.Button>
                </Radio.Group>
              </Tooltip>
            </Form.Item>
          </Col>
        </Row>
      </Form>
      <DocumentGrid
        isLoading={loading}
        documents={filteredAndSearchedDocuments}
      />
    </Layout>
  )
}

export default DocumentsList
