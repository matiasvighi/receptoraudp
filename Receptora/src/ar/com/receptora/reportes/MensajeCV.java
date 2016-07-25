package ar.com.receptora.reportes;

import java.util.Date;

public class MensajeCV extends Mensaje{
	
	private int nroReporte;
	private Date fecha;
	private long latitud;
	private long longitud;
	private int velocidad;
	private String Rumbo;
	private String estadoEntradasDigitales;
	private String estadoSalidasDigitales;
	private int tensionBateria;
	private String contadorOdometroTotal;
	private boolean gpsPowerStatus;
	private int gpsFixMode;
	private int pdop;
	private int cantidadSatelites;
	private String segundosDesdeUltimaPos;
	private boolean modemPowerStatus;
	private int estadoRegistroGsm;
	private int nivelDeSenalCEL;
	private int temperaturaSensor0;
	private int segundosDesdeUltimaMedicionSensor0;
	private int temperaturaSensor1;
	private int segundosDesdeUltimaMedicionSensor1;
	private String nroMensaje;
	
	/**
	 * @return the nroReporte
	 */
	public int getNroReporte() {
		return nroReporte;
	}
	/**
	 * @param nroReporte the nroReporte to set
	 */
	public void setNroReporte(int nroReporte) {
		this.nroReporte = nroReporte;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the latitud
	 */
	public long getLatitud() {
		return latitud;
	}
	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}
	/**
	 * @return the longitud
	 */
	public long getLongitud() {
		return longitud;
	}
	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}
	/**
	 * @return the velocidad
	 */
	public int getVelocidad() {
		return velocidad;
	}
	/**
	 * @param velocidad the velocidad to set
	 */
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	/**
	 * @return the rumbo
	 */
	public String getRumbo() {
		return Rumbo;
	}
	/**
	 * @param rumbo the rumbo to set
	 */
	public void setRumbo(String rumbo) {
		Rumbo = rumbo;
	}
	/**
	 * @return the estadoEntradasDigitales
	 */
	public String getEstadoEntradasDigitales() {
		return estadoEntradasDigitales;
	}
	/**
	 * @param estadoEntradasDigitales the estadoEntradasDigitales to set
	 */
	public void setEstadoEntradasDigitales(String estadoEntradasDigitales) {
		this.estadoEntradasDigitales = estadoEntradasDigitales;
	}
	/**
	 * @return the estadoSalidasDigitales
	 */
	public String getEstadoSalidasDigitales() {
		return estadoSalidasDigitales;
	}
	/**
	 * @param estadoSalidasDigitales the estadoSalidasDigitales to set
	 */
	public void setEstadoSalidasDigitales(String estadoSalidasDigitales) {
		this.estadoSalidasDigitales = estadoSalidasDigitales;
	}
	/**
	 * @return the tensionBateria
	 */
	public int getTensionBateria() {
		return tensionBateria;
	}
	/**
	 * @param tensionBateria the tensionBateria to set
	 */
	public void setTensionBateria(int tensionBateria) {
		this.tensionBateria = tensionBateria;
	}
	/**
	 * @return the contadorOdometroTotal
	 */
	public String getContadorOdometroTotal() {
		return contadorOdometroTotal;
	}
	/**
	 * @param contadorOdometroTotal the contadorOdometroTotal to set
	 */
	public void setContadorOdometroTotal(String contadorOdometroTotal) {
		this.contadorOdometroTotal = contadorOdometroTotal;
	}
	/**
	 * @return the gpsPowerStatus
	 */
	public boolean isGpsPowerStatus() {
		return gpsPowerStatus;
	}
	/**
	 * @param gpsPowerStatus the gpsPowerStatus to set
	 */
	public void setGpsPowerStatus(boolean gpsPowerStatus) {
		this.gpsPowerStatus = gpsPowerStatus;
	}
	/**
	 * @return the gpsFixMode
	 */
	public int getGpsFixMode() {
		return gpsFixMode;
	}
	/**
	 * @param gpsFixMode the gpsFixMode to set
	 */
	public void setGpsFixMode(int gpsFixMode) {
		this.gpsFixMode = gpsFixMode;
	}
	/**
	 * @return the pdop
	 */
	public int getPdop() {
		return pdop;
	}
	/**
	 * @param pdop the pdop to set
	 */
	public void setPdop(int pdop) {
		this.pdop = pdop;
	}
	/**
	 * @return the cantidadSatelites
	 */
	public int getCantidadSatelites() {
		return cantidadSatelites;
	}
	/**
	 * @param cantidadSatelites the cantidadSatelites to set
	 */
	public void setCantidadSatelites(int cantidadSatelites) {
		this.cantidadSatelites = cantidadSatelites;
	}
	/**
	 * @return the segundosDesdeUltimaPos
	 */
	public String getSegundosDesdeUltimaPos() {
		return segundosDesdeUltimaPos;
	}
	/**
	 * @param segundosDesdeUltimaPos the segundosDesdeUltimaPos to set
	 */
	public void setSegundosDesdeUltimaPos(String segundosDesdeUltimaPos) {
		this.segundosDesdeUltimaPos = segundosDesdeUltimaPos;
	}
	/**
	 * @return the modemPowerStatus
	 */
	public boolean isModemPowerStatus() {
		return modemPowerStatus;
	}
	/**
	 * @param modemPowerStatus the modemPowerStatus to set
	 */
	public void setModemPowerStatus(boolean modemPowerStatus) {
		this.modemPowerStatus = modemPowerStatus;
	}
	/**
	 * @return the estadoRegistroGsm
	 */
	public int getEstadoRegistroGsm() {
		return estadoRegistroGsm;
	}
	/**
	 * @param estadoRegistroGsm the estadoRegistroGsm to set
	 */
	public void setEstadoRegistroGsm(int estadoRegistroGsm) {
		this.estadoRegistroGsm = estadoRegistroGsm;
	}
	/**
	 * @return the nivelDeSenalCEL
	 */
	public int getNivelDeSenalCEL() {
		return nivelDeSenalCEL;
	}
	/**
	 * @param nivelDeSenalCEL the nivelDeSenalCEL to set
	 */
	public void setNivelDeSenalCEL(int nivelDeSenalCEL) {
		this.nivelDeSenalCEL = nivelDeSenalCEL;
	}
	/**
	 * @return the temperaturaSensor0
	 */
	public int getTemperaturaSensor0() {
		return temperaturaSensor0;
	}
	/**
	 * @param temperaturaSensor0 the temperaturaSensor0 to set
	 */
	public void setTemperaturaSensor0(int temperaturaSensor0) {
		this.temperaturaSensor0 = temperaturaSensor0;
	}
	/**
	 * @return the segundosDesdeUltimaMedicionSensor0
	 */
	public int getSegundosDesdeUltimaMedicionSensor0() {
		return segundosDesdeUltimaMedicionSensor0;
	}
	/**
	 * @param segundosDesdeUltimaMedicionSensor0 the segundosDesdeUltimaMedicionSensor0 to set
	 */
	public void setSegundosDesdeUltimaMedicionSensor0(int segundosDesdeUltimaMedicionSensor0) {
		this.segundosDesdeUltimaMedicionSensor0 = segundosDesdeUltimaMedicionSensor0;
	}
	/**
	 * @return the temperaturaSensor1
	 */
	public int getTemperaturaSensor1() {
		return temperaturaSensor1;
	}
	/**
	 * @param temperaturaSensor1 the temperaturaSensor1 to set
	 */
	public void setTemperaturaSensor1(int temperaturaSensor1) {
		this.temperaturaSensor1 = temperaturaSensor1;
	}
	/**
	 * @return the segundosDesdeUltimaMedicionSensor1
	 */
	public int getSegundosDesdeUltimaMedicionSensor1() {
		return segundosDesdeUltimaMedicionSensor1;
	}
	/**
	 * @param segundosDesdeUltimaMedicionSensor1 the segundosDesdeUltimaMedicionSensor1 to set
	 */
	public void setSegundosDesdeUltimaMedicionSensor1(int segundosDesdeUltimaMedicionSensor1) {
		this.segundosDesdeUltimaMedicionSensor1 = segundosDesdeUltimaMedicionSensor1;
	}
	/**
	 * @return the nroMensaje
	 */
	public String getNroMensaje() {
		return nroMensaje;
	}
	/**
	 * @param nroMensaje the nroMensaje to set
	 */
	public void setNroMensaje(String nroMensaje) {
		this.nroMensaje = nroMensaje;
	}
}